package cn.sgf.asset.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.CollarDO;

@Repository
public interface CollarDao extends JpaRepository<CollarDO, Long> {

}