package cn.sgf.asset.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.CheckInfoDO;

@Repository
public interface CheckInfoDao extends JpaRepository<CheckInfoDO, Long> {
	
}