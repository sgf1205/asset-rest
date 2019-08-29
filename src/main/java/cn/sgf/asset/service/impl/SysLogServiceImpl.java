package cn.sgf.asset.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.joran.action.ActionUtil;
import cn.sgf.asset.core.enu.SysOpsTypeEnum;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.SysLogDao;
import cn.sgf.asset.domain.SysLogDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService{
	
	@Autowired
	private SysLogDao sysLogDao;

	@Override
	public void save(UserDO opsUser,SysOpsTypeEnum sysOpsEnum,String describe) {
		// TODO Auto-generated method stub
		SysLogDO sysLogDo=new SysLogDO();
		sysLogDo.setOpsTime(new Date());
		sysLogDo.setOpsUser(opsUser);
		sysLogDo.setType(sysOpsEnum.getCode());
		sysLogDo.setDescribe(describe);
		sysLogDao.save(sysLogDo);
	}

	
	@Override
	public void save(UserDO opsUser,SysOpsTypeEnum sysOpsEnum) {
		// TODO Auto-generated method stub
		save(opsUser, sysOpsEnum,sysOpsEnum.getDesc());
	}
	
	@Override
	public void save(String token, SysOpsTypeEnum sysOpsEnum) {
		// TODO Auto-generated method stub
		UserDTO user=AuthUtil.getUserByToken(token);
		UserDO userDo=new UserDO();
		userDo.setId(user.getId());
		save(userDo, sysOpsEnum);
	}
	@Override
	public void save(Long userId, SysOpsTypeEnum sysOpsEnum,String describe) {
		// TODO Auto-generated method stub
		UserDO userDo=new UserDO();
		userDo.setId(userId);
		save(userDo, sysOpsEnum,describe);
	}
}
