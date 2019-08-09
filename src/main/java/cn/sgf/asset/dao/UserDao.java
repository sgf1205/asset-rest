package cn.sgf.asset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.UserDO;

@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {
	
	@EntityGraph(value = "user.all", type = EntityGraph.EntityGraphType.FETCH)
	List<UserDO> findByDeleteFlag(int deleteFlag);
}