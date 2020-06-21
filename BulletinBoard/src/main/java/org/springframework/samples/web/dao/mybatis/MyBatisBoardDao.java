package org.springframework.samples.web.dao.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.web.domain.Board;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.domain.Reply;
import org.springframework.stereotype.Repository;

@Repository
public class MyBatisBoardDao implements BoardDao {
	@Autowired //객체 new이용 안해도 자동 주입 
	private SqlSession sqlSession;
	
	
	private static final Logger log = LoggerFactory.getLogger(MyBatisBoardDao.class);


	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public void create(Board board) throws DataAccessException{ // user에도 create가 있는데 이거를 하나의 클래스로 정의하고 그 클래스를 상속받아 메소드를 재정의 하면 되지 않을까???
		// TODO Auto-generated method stub
		sqlSession.insert("boardMapper.create", board);

	}

	@Override
	public void delete(Board board) throws DataAccessException{
		// TODO Auto-generated method stub
		sqlSession.delete("boardMapper.delete", board);
	}

	@Override
	public void update(Board board) throws DataAccessException{
		// TODO Auto-generated method stub
		log.debug("update {}", board);
		sqlSession.update("boardMapper.update", board);
	}
	

	@Override
	public List<Board> boardList(BoardPager boardPager) throws DataAccessException{
		// TODO Auto-generated method stub
		
		return  sqlSession.selectList("boardMapper.boardList", boardPager);
	}

	@Override
	public Board findByNo(int no) throws DataAccessException{
		// TODO Auto-generated method stub
		sqlSession.update("boardMapper.updateSee", no);
		return sqlSession.selectOne("boardMapper.findByBoard", no);
	}

	@Override
	public int listCount() throws DataAccessException{
		// TODO Auto-generated method stub
		return sqlSession.selectOne("boardMapper.boardListCount");
	}

	@Override
	public void insert(Reply reply) throws DataAccessException {
		// TODO Auto-generated method stub
		log.debug("reply transfer{}", reply);
		sqlSession.insert("replyMapper.insert", reply);
		
	}

	@Override
	public List<Reply> replyList(int boardNo) throws DataAccessException {
		// TODO Auto-generated method stub
		log.debug("마이바티스 접근{}", boardNo);
		return sqlSession.selectList("replyMapper.selectByBoardNo", boardNo);
	}

	@Override
	public Reply replyOne(int boardNo) throws DataAccessException {
		// TODO Auto-generated method stub
		log.debug("들어왔나?{}", boardNo);
		return sqlSession.selectOne("replyMapper.selectReplyRecent", boardNo);
	}

	@Override
	public void replyDelete(int replyNo) {
		// TODO Auto-generated method stub
		sqlSession.delete("replyMapper.replyDelete", replyNo);
		
	}
	
	

}
