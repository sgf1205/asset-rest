package cn.sgf.asset.contorller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.UserDTO;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

	private Logger logger = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetDao assetDao;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, AssetDTO assetDto) {
		logger.info("asset:{}", assetDto);
		AssetDO assetDo = new AssetDO();
		BeanUtils.copyProperties(assetDto, assetDo);
		ClassesDO classesDo = new ClassesDO();
		classesDo.setId(assetDto.getClassesId());
		assetDo.setClasses(classesDo);
		UserDTO currentUserDto = AuthUtil.getUserByToken(token);
		UserDO userDo = new UserDO();
		userDo.setId(currentUserDto.getId());
		SysOrganDO organ=new SysOrganDO();
		organ.setId(assetDto.getOrganId());
		assetDo.setRegisterOrgan(organ);
		if (assetDo.getId() == null) {//新增
			assetDo.setRegisterUser(userDo);
			assetDo.setRegisterTime(new Date());
			assetDo.setStatus(StatusEnum.FREE.getCode());
			assetDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
			assetDo.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
		}else {//修改
			assetDo.setEditUser(userDo);
			assetDo.setEditTime(new Date());
		}
		assetDao.save(assetDo);
		return RespInfo.success();
	}

	@RequestMapping("/delete")
	public RespInfo del(Long[] ids) {
		for (Long id : ids) {
			AssetDO assetDo = assetDao.getOne(id);
			assetDo.setDeleteFlag(DeleteEnum.DELETED.getCode());
			assetDao.save(assetDo);
		}
		return RespInfo.success();
	}

	@RequestMapping("/list")
	public RespInfo list(@RequestParam(required = false) String name,@RequestParam(required = false) Integer status, PageParam pageParam) {
		Pageable pageable = PageRequest.of(pageParam.getCurrentPage() - 1, pageParam.getPageSize());
		AssetDO searchDo = new AssetDO();
		if (StringUtils.isNotEmpty(name)) {
			searchDo.setName(name);
		}
		if(status!=null) {
			searchDo.setStatus(status);
		}
		searchDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("name",
				GenericPropertyMatchers.contains());
		Example<AssetDO> searchExample = Example.of(searchDo, exampleMatcher);
		Page<AssetDO> pageList = assetDao.findAll(searchExample, pageable);
		List<AssetDTO> dtoList = pageList.getContent().stream().map(assetDo -> {
			AssetDTO dto = new AssetDTO();
			BeanUtils.copyProperties(assetDo, dto);
			dto.setClassesName(assetDo.getClasses().getName());
			dto.setClassesId(assetDo.getClasses().getId());
			dto.setRegisterUserName(assetDo.getRegisterUser().getName());
			dto.setOrganId(assetDo.getRegisterOrgan().getId());
			dto.setOrganName(assetDo.getRegisterOrgan().getName());
			return dto;
		}).collect(Collectors.toList());
		PageResult<AssetDTO> pageResult=new PageResult<AssetDTO>();
		pageResult.setResult(dtoList);
		pageResult.setTotalPage(pageList.getTotalPages());
		pageResult.setTotalSize(pageList.getTotalElements());
		pageResult.setCurrentPage(pageList.getNumber());
		return RespInfo.success(pageResult);
	}
}
