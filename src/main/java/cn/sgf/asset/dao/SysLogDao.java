package cn.sgf.asset.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.SysLogDO;

@Repository
public interface SysLogDao extends JpaRepository<SysLogDO, Long> {

	List<SysLogDO> findFirst10ByOrderByOpsTimeDesc();
	
}