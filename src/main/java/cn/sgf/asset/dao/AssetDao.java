package cn.sgf.asset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.UserDO;

@Repository
public interface AssetDao extends JpaRepository<AssetDO, Long> {

	List<AssetDO> findByDeleteFlag(int code);
}