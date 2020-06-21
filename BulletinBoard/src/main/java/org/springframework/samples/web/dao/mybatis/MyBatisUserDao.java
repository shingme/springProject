package org.springframework.samples.web.dao.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.web.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisUserDao implements UserDao {
	@Autowired
	private SqlSession sqlSession; //mybatis 사용 시 꼭 필요한 객체 
		
	//sqlSeesionFactory --> mybatis 설정 파일을 읽어 초기화 수행 
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	@Override
	public User findById(String userId) throws DataAccessException{
		//selectOne 질의 실행 후 하나의 결과 객체들을 반환 
		return sqlSession.selectOne("userMapper.findById", userId);
	}

	@Override
	public void create(User user) throws DataAccessException{
		sqlSession.insert("userMapper.create", user);

	}

	@Override
	public void update(User user) throws DataAccessException{
		
		sqlSession.update("userMapper.update", user);

	}

}
