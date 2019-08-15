package cn.sgf.asset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.ClassesDO;

@Repository
public interface ClassesDao extends JpaRepository<ClassesDO, Long> {

	List<ClassesDO> findByDeleteFlag(int code);
	
}