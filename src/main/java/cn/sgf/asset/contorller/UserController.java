package cn.sgf.asset.contorller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.joran.action.ActionUtil;
import cn.sgf.asset.core.config.UserConfig;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.RoleEnum;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.UserDTO;


@RestController
@RequestMapping("/api/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserConfig userConfig;
	
	
	
	@RequestMapping("/save")
	public RespInfo save(UserDTO userDto) {
		logger.info("userDto:{}",userDto);
		if(userDto.getRoleId()==AuthUtil.getAdminUser().getRoleId()) {
			return RespInfo.fail("不能修改超级管理员信息");
		}
		UserDO userDo=new UserDO();
		BeanUtils.copyProperties(userDto, userDo);
		if(userDto.getOrganId()!=null) {
			SysOrganDO organ=new SysOrganDO();
			organ.setId(userDto.getOrganId());
			userDo.setOrgan(organ);
		}
		userDo.setPwd(userConfig.getDefaultPwd());
		userDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		userDao.save(userDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/restPwd")
	public RespInfo restPwd(Long id) {
		UserDO userDo=userDao.getOne(id);
		userDo.setPwd(userConfig.getDefaultPwd());
		userDao.save(userDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/editSelfPwd")
	public RespInfo editSelfPwd(@RequestHeader("token")String token,String oldPassword,String newPassword) {
		UserDTO userDto=AuthUtil.getUserByToken(token);
		if(userDto.getRoleId()==AuthUtil.getAdminUser().getRoleId())
			return RespInfo.fail("超级管理员不能修改密码！");
		if(!oldPassword.equals(userDto.getPwd()))
			return RespInfo.fail("原密码错误！");
		if(!newPassword.equals(userDto.getPwd())) {
			userDto.setPwd(newPassword);
			userDao.editPwd(newPassword,userDto.getId());
		}
		return RespInfo.success();
	}
	
	@RequestMapping("/delete")
	public RespInfo delete(Long id) {
		logger.info("delete user id:{}",id);
		UserDO userDo=userDao.getOne(id);
		userDo.setDeleteFlag(DeleteEnum.DELETED.getCode());
		userDao.save(userDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/list")
	public RespInfo list() {
		List<UserDO> users=userDao.findByDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		List<UserDTO> dtos=users.stream().map(userDo->{
			UserDTO dto=new UserDTO();
			BeanUtils.copyProperties(userDo, dto);
			if(userDo.getOrgan()!=null) {
				dto.setOrganId(userDo.getOrgan().getId());
				dto.setOrganName(userDo.getOrgan().getName());
			}
			dto.setRoleName(RoleEnum.getByCode(dto.getRoleId()).getName());
			return dto;
		}).collect(Collectors.toList());
		return RespInfo.success(dtos);
	}
}
