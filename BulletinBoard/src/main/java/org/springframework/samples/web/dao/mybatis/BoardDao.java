package org.springframework.samples.web.dao.mybatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.web.domain.Board;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.domain.Reply;

public interface BoardDao {
	void create(Board board) throws DataAccessException;
	
	void delete(Board board) throws DataAccessException;
	
	void update(Board board) throws DataAccessException;
	
	List<Board> boardList(BoardPager boardPager) throws DataAccessException;
	
	Board findByNo(int no) throws DataAccessException;

	int listCount() throws DataAccessException;
	
	void insert(Reply reply) throws DataAccessException;
	
	List<Reply> replyList(int boardNo) throws DataAccessException;
	
	Reply replyOne(int boardNo) throws DataAccessException;

	void replyDelete(int replyNo);

}
