package cn.sgf.asset.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.ApplyDO;

@Repository
public interface ApplyDao extends JpaRepository<ApplyDO, Long>, JpaSpecificationExecutor<ApplyDO> {

}