package cn.sgf.asset.contorller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.RespInfo;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.UserDTO;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
	
	private Logger logger=LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetDao assetDao;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token")String token,AssetDTO assetDto) {
		logger.info("asset:{}",assetDto);
		AssetDO assetDo=new AssetDO();
		BeanUtils.copyProperties(assetDto, assetDo);
		assetDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		ClassesDO classesDo=new ClassesDO();
		classesDo.setId(assetDto.getClassesId());
		assetDo.setClasses(classesDo);
		UserDTO currentUserDto=AuthUtil.getUserByToken(token);
		UserDO userDo=new UserDO();
		userDo.setId(currentUserDto.getId());
		if(assetDo.getId()==null) {
			assetDo.setRegisterUser(userDo);
			assetDo.setRegisterTime(new Date());
			assetDo.setStatus(StatusEnum.FREE.getCode());
			assetDo.setCode(UUID.randomUUID().toString().replaceAll("-",""));
		}
		assetDao.save(assetDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/delete")
	public RespInfo del(Long[] ids) {
		for(Long id:ids) {
			AssetDO assetDo=assetDao.getOne(id);
			assetDo.setDeleteFlag(DeleteEnum.DELETED.getCode());
			assetDao.save(assetDo);
		}
		return RespInfo.success();
	}
	
	@RequestMapping("/list")
	public RespInfo list() {
		List<AssetDO> list=assetDao.findByDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		List<AssetDTO> dtoList=list.stream().map(assetDo->{
			AssetDTO dto=new AssetDTO();
			BeanUtils.copyProperties(assetDo, dto);
			dto.setClassesName(assetDo.getClasses().getName());
			dto.setRegisterUserName(assetDo.getRegisterUser().getName());
			return dto;
		}).collect(Collectors.toList());
		return RespInfo.success(dtoList);
	}
}
