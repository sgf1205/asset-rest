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

import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.RoleConfigDao;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.RoleConfigDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleConfigDao roleConfigDao;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	@RequestMapping("/login")
	public RespInfo login(String username,String password) {
		logger.info("username:{},password:{}",username,password);
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
			return RespInfo.success(userDto);
		}
	}
	
	@RequestMapping("/logout")
	public RespInfo logout(@RequestHeader(name="token")String token) {
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
