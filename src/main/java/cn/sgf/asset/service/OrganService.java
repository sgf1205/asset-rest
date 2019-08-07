package cn.sgf.asset.service;

import java.util.List;

import cn.sgf.asset.domain.SysOrganDO;


public interface OrganService {
	List<SysOrganDO> listByPid(Long id);
}
