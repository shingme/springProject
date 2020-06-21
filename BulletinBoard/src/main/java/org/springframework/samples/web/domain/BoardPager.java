package org.springframework.samples.web.domain;

import org.apache.ibatis.type.Alias;

@Alias("boardPager")
public class BoardPager {
	private int start;
	private int end;
	private int tempStart; // 한블록 페이지 시작 
	private int tempEnd; // 한블록 페이지 마지
	private boolean prev, next;
	
	public BoardPager() {
	
	}
	public BoardPager(int start, int end, int tempStart, int tempEnd, boolean prev, boolean next) {
		this.start = start;
		this.end = end;
		this.tempStart = tempStart;
		this.tempEnd = tempEnd;
		this.prev = prev;
		this.next = next;
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getTempStart() {
		return tempStart;
	}
	public void setTempStart(int tempStart) {
		this.tempStart = tempStart;
	}
	public int getTempEnd() {
		return tempEnd;
	}
	public void setTempEnd(int tempEnd) {
		this.tempEnd = tempEnd;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	

}
