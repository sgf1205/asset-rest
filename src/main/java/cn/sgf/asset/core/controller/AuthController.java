package cn.sgf.asset.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.enu.SysOpsTypeEnum;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.RoleConfigDao;
import cn.sgf.asset.dao.SysLogDao;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.RoleConfigDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.SysLogService;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private RoleConfigDao roleConfigDao;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	@RequestMapping("/login")
	public RespInfo login(String username,String password) {
		logger.info("username:{},password:{}",username,password);
		if(AuthUtil.alreadyOnline(username)) {
			return RespInfo.fail("该用户已登陆，不能重复登陆");
		}
		if(AuthUtil.getAdminUser().getAccount().equals(username) &&
				AuthUtil.getAdminUser().getPassword().equals(password)) {
			UserDTO userDto=new UserDTO();
			userDto.setRoleId(AuthUtil.getAdminUser().getRoleId());
			userDto.setName(username);
			userDto.setAccount(username);
			userDto.setName(AuthUtil.getAdminUser().getName());
			AuthUtil.save(userDto);
			return RespInfo.success(userDto);
		}else {
			UserDO user=userDao.findByAccountAndPwd(username,password);
			if(user==null) {
				return RespInfo.fail("用户名密码错误");
			}
			UserDTO userDto=new UserDTO();
			BeanUtils.copyProperties(user, userDto);
			List<RoleConfigDO> roleConfigDo=roleConfigDao.findByRoleId(userDto.getRoleId());
			List<String> menus=roleConfigDo.stream().map(rc-> rc.getMenu()).collect(Collectors.toList());
			userDto.setMenus(menus);
			if(user.getOrgan()!=null) {
				userDto.setOrganId(user.getOrgan().getId());
				userDto.setOrganName(user.getOrgan().getName());
			}
			AuthUtil.save(userDto);
			sysLogService.save(user, SysOpsTypeEnum.LOGIN);
			return RespInfo.success(userDto);
		}
	}
	
	@RequestMapping("/logout")
	public RespInfo logout(@RequestHeader(name="token")String token) {
		UserDTO userDto=AuthUtil.getUserByToken(token);
		if(userDto.getRoleId()!=AuthUtil.getAdminUser().getRoleId()) {
			sysLogService.save(token, SysOpsTypeEnum.LOGOUT);
		}
		AuthUtil.remove(token);
		return RespInfo.success();
	}
	
	@RequestMapping("/getCurrentUser")
	public RespInfo getCurrentUser(@RequestHeader(name="token")String token) {
		UserDTO user=AuthUtil.getUserByToken(token);
		return RespInfo.success(user);
	}
	
	@RequestMapping("/menus")
	public RespInfo getMenus(@RequestHeader(name="token")String token) {
		UserDTO userDto=AuthUtil.getUserByToken(token);
		return RespInfo.success(userDto.getMenus());
	}
}
