package cn.sgf.asset.contorller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.RespInfo;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.UserDO;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/save")
	public RespInfo save(UserDO userDo) {
		logger.info("userDo:{}",userDo);
		userDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		
		userDao.save(userDo);
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
		return RespInfo.success(users);
	}
}
