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
import cn.sgf.asset.dto.AssetStatisticsDTO;

@Repository
public interface AssetDao extends JpaRepository<AssetDO, Long>,JpaSpecificationExecutor<AssetDO> {

	List<AssetDO> findByDeleteFlag(int code);
	
	@Modifying
	@Query("update AssetDO u set u.status = :status where u.id = :id")
	void editStatus(Integer status, Long id);
	
	@Modifying
	@Query("update AssetDO u set u.status = :status,u.usingOrgan=:usingOrgan where u.id = :id")
	void editStatus(Integer status,SysOrganDO usingOrgan, Long id);
	
	@Query("select new cn.sgf.asset.dto.AssetStatisticsDTO( a.classes.name,"
			+ "sum(case a.status when 0 then 1 else 0 end) ,"
			+ "sum(case a.status when 2 then 1 else 0 end) ,"
			+ "sum(case a.status when 3 then 1 else 0 end) ,"
			+ "sum(case a.status when 4 then 1 else 0 end) ,"
			+ "sum(case a.status when 5 then 1 else 0 end) ,"
			+ "sum(a.money)) "
			+ " from AssetDO a group by a.classes")
	List<AssetStatisticsDTO> statisticsByClasses();
	
	@Query("select new cn.sgf.asset.dto.AssetStatisticsDTO( a.usingOrgan.name,"
			+ "sum(case a.status when 0 then 1 else 0 end) ,"
			+ "sum(case a.status when 2 then 1 else 0 end) ,"
			+ "sum(case a.status when 3 then 1 else 0 end) ,"
			+ "sum(case a.status when 4 then 1 else 0 end) ,"
			+ "sum(case a.status when 5 then 1 else 0 end) ,"
			+ "sum(a.money)) "
			+ " from AssetDO a group by a.usingOrgan")
	List<AssetStatisticsDTO> statisticsByUsingOrgan();
}