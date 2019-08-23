package cn.sgf.asset.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.ApplyDO;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.SysOrganDO;

@Repository
public interface AssetDao extends JpaRepository<AssetDO, Long>,JpaSpecificationExecutor<AssetDO> {

	List<AssetDO> findByDeleteFlag(int code);
	
	@Modifying
	@Query("update AssetDO u set u.status = :status where u.id = :id")
	void editStatus(Integer status, Long id);
	
	@Modifying
	@Query("update AssetDO u set u.status = :status,u.usingOrganId=:usingOrgan where u.id = :id")
	void editStatus(Integer status,SysOrganDO usingOrgan, Long id);
}