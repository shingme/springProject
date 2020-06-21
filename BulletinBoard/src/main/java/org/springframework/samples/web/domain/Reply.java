package org.springframework.samples.web.domain;


import org.apache.ibatis.type.Alias;
@Alias("reply")
public class Reply {
	private int no;
	private int boardNo;
	private String id;
	private String date;
	private String comment;
	
	
	
	public Reply(){
		
	}
	public Reply(int no, int boardNo, String id, String date, String comment) {
		this.no = no;
		this.boardNo = boardNo;
		this.id = id;
		this.date = date;
		this.comment = comment;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Reply [no=" + no + ", boardNo=" + boardNo + ", id=" + id + ", date=" + date + ", comment=" + comment
				+ "]";
	}
	
	
	
	

}
