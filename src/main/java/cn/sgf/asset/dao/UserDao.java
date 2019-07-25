package cn.sgf.asset.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.sgf.asset.domain.UserDO;

@Repository
public interface UserDao extends JpaRepository<UserDO, Long> {
}