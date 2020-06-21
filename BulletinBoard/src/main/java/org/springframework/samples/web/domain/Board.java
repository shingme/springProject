package org.springframework.samples.web.domain;

import org.apache.ibatis.type.Alias;
import org.hibernate.validator.constraints.NotEmpty;

@Alias("board")
public class Board {
		private int b_no; //게시판 번호 
		private String b_id;
		@NotEmpty
		private String b_title;
		@NotEmpty
		private String b_text;
		private String b_date;
		private int b_see;
		
		public Board() {
			
		}
		public Board(int b_no, String b_id, String b_title, String b_text, String b_date, int b_see) {
			this.b_no = b_no;
			this.b_id = b_id;
			this.b_title = b_title;
			this.b_text = b_text;
			this.b_date = b_date;
			this.b_see = b_see;
		}

		public int getB_no() {
			return b_no;
		}



		public void setB_no(int b_no) {
			this.b_no = b_no;
		}



		public String getB_id() {
			return b_id;
		}

		public void setB_id(String b_id) {
			this.b_id = b_id;
		}

		public String getB_title() {
			return b_title;
		}

		public void setB_title(String b_title) {
			this.b_title = b_title;
		}

		public String getB_text() {
			return b_text;
		}

		public void setB_text(String b_text) {
			this.b_text = b_text;
		}

		public String getB_date() {
			return b_date;
		}

		public void setB_date(String b_date) {
			this.b_date = b_date;
		}

		public int getB_see() {
			return b_see;
		}

		public void setB_see(int b_see) {
			this.b_see = b_see;
		}

		@Override
		public String toString() {
			return "Board [b_id=" + b_id + ", b_title=" + b_title + ", b_text=" + b_text + ", b_date=" + b_date
					+ ", b_see=" + b_see + "]";
		}
		
		
		
		
	

}
