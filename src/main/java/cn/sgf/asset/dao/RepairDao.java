package cn.sgf.asset.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.RepairDO;
import cn.sgf.asset.domain.ScrapDO;

@Repository
public interface RepairDao extends JpaRepository<RepairDO, Long>, JpaSpecificationExecutor<RepairDO> {

}