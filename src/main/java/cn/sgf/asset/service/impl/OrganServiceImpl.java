package cn.sgf.asset.service.impl;

import java.util.List;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sgf.asset.dao.OrganDao;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.service.OrganService;

@Service
public class OrganServiceImpl implements OrganService{

	@Autowired
	private OrganDao organDao;
	
	@Override
	public List<SysOrganDO> listByPid(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
