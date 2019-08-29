package cn.sgf.asset.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.dao.RoleConfigDao;
import cn.sgf.asset.dao.SysLogDao;
import cn.sgf.asset.domain.RoleConfigDO;
import cn.sgf.asset.domain.SysLogDO;
import cn.sgf.asset.dto.RoleConfigDTO;
import cn.sgf.asset.dto.UserDTO;

@RestController
@RequestMapping("/api/sys/log")
public class SysLogController {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	
	@RequestMapping("/list")
	public RespInfo list(@RequestHeader(name="token")String token) {
		List<SysLogDO> logList=sysLogDao.findFirst10ByOrderByOpsTimeDesc();
		return RespInfo.success(logList);
	}
	
}
