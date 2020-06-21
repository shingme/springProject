package org.springframework.samples.web.dao.mybatis;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.web.domain.User;
//mybatis로 연결한 것 
public interface UserDao {
	
	User findById(String userId) throws DataAccessException;;

	void create(User user) throws DataAccessException;;

	void update(User user) throws DataAccessException;;

}