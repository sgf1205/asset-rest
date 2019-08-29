package cn.sgf.asset.service;

import cn.sgf.asset.core.enu.SysOpsTypeEnum;
import cn.sgf.asset.domain.UserDO;

public interface SysLogService {

	public void save(UserDO opsUser,SysOpsTypeEnum sysOpsEnum,String describe);
	
	public void save(UserDO opsUser,SysOpsTypeEnum sysOpsEnum);
	
	public void save(String token,SysOpsTypeEnum sysOpsEnum);
	
	public void save(Long userId,SysOpsTypeEnum sysOpsEnum,String describe);
}
