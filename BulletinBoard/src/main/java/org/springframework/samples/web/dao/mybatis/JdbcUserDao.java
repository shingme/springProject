package org.springframework.samples.web.dao.mybatis;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.samples.web.domain.User;

//jdbc로 연결한 것 -> JdbcTemplate 이용한 것!!!
public class JdbcUserDao extends JdbcDaoSupport implements UserDao{
	
	private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);
	/*
	@PostConstruct
	public void initialize(){ //자동 호출 되면서 초기화가 진행
		log.info("userDao");
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("mysql.sql")); //SQL문 실행 
		DatabasePopulatorUtils.execute(populator, getDataSource());
		log.info("database initialized success!");
	}
	*/

	/* (non-Javadoc)
	 * @see org.springframework.samples.web.dao.users.IUserDao#findById(java.lang.String)
	 */
	@Override
	public User findById(String userId) {
		log.debug("findById");
		// TODO Auto-generated method stub
		String sql = "select * from USERS where userId = ?";
		RowMapper<User> rowMapper = new RowMapper<User>(){
			
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				//매핑 
				
				return new User(
							rs.getString("userId"),
							rs.getString("password"),
							rs.getString("nickname"),
							rs.getString("name"),
							rs.getString("email"));
				}
		};
		try{
			//Sql과 rowmapper를 전달할 수 있다.   
			return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.samples.web.dao.users.IUserDao#create(org.springframework.samples.web.domain.User)
	 */
	@Override
	public void create(User user) {
		// TODO Auto-generated method stub
		String sql = "insert into USERS values(?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getNickname(), user.getName(), user.getEmail());
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.samples.web.dao.users.IUserDao#update(org.springframework.samples.web.domain.User)
	 */
	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		String sql = "update USERS set password = ?, nickname = ?, name = ?, email = ? where userId = ?";
		getJdbcTemplate().update(sql, user.getPassword(), user.getNickname(), user.getName(), user.getEmail(), user.getUserId());
		
		
	}
}
