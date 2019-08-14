package cn.sgf.asset.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.UserDO;

@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {
	
	@EntityGraph(value = "user.all", type = EntityGraph.EntityGraphType.FETCH)
	List<UserDO> findByDeleteFlag(int deleteFlag);

	UserDO findByAccountAndPwd(String account, String password);

	@Transactional
	@Modifying
	@Query("update UserDO u set u.pwd = :newPwd where u.id = :id")
	void editPwd(String newPwd, Long id);
}