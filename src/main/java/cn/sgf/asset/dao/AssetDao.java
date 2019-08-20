package cn.sgf.asset.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.AssetDO;

@Repository
public interface AssetDao extends JpaRepository<AssetDO, Long> {

	List<AssetDO> findByDeleteFlag(int code);
	
	@Transactional
	@Modifying
	@Query("update AssetDO u set u.status = :status where u.id = :id")
	void editStatus(Integer status, Long id);
}