package cn.sgf.asset.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.RoleConfigDO;

@Repository
public interface RoleConfigDao extends JpaRepository<RoleConfigDO, Long> {

	@Transactional
	void deleteByRoleId(Long roleId);

	List<RoleConfigDO> findByRoleId(Long roleId);
	
}