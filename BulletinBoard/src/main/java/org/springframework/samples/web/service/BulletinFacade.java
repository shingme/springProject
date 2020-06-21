package org.springframework.samples.web.service;

import java.io.IOException;
import java.util.List;

import org.springframework.samples.web.domain.Article;
import org.springframework.samples.web.domain.Board;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.domain.Reply;
import org.springframework.samples.web.domain.User;

public interface BulletinFacade {
	User findById(String userId);

	void create(User user);

	void update(User user);
	
	void create(Board board);
	
	void delete(Board board);
	
	void update(Board board);
	
	int boardCount();
			
	Board findByNo(int no);
	
	List<Board> boardList(int page, int count, BoardPager boardPager);
	
	void insert(Reply reply);
	
	void replyDelete(int replyNo);
	
	List<Reply> replyList(int boardNo);
	
	Reply replyOne(int boardNo);
	
	List<Article> articleList(String keyword, int page, BoardPager pager);
		

}
