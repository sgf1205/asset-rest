package cn.sgf.asset.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.dao.RoleConfigDao;
import cn.sgf.asset.domain.RoleConfigDO;
import cn.sgf.asset.dto.RoleConfigDTO;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	@Autowired
	private RoleConfigDao roleConfigDao;
	
	@RequestMapping("/config")
	public RespInfo config(RoleConfigDTO dto) {
		roleConfigDao.deleteByRoleId(dto.getRoleId());
		for(String menu:dto.getMenus()) {
			RoleConfigDO roleConfigDo=new RoleConfigDO();
			roleConfigDo.setMenu(menu);
			roleConfigDo.setRoleId(dto.getRoleId());
			roleConfigDo.setRoleName(dto.getRoleName());
			roleConfigDao.save(roleConfigDo);
		}
		return RespInfo.success();
	}
	
	@RequestMapping("/list")
	public RespInfo list(Long roleId) {
		List<RoleConfigDO> roleConfigs=roleConfigDao.findByRoleId(roleId);
		return RespInfo.success(roleConfigs);
	}
	
}
