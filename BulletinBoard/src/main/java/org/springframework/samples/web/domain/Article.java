package org.springframework.samples.web.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Article {
	
	static SimpleDateFormat FORMATTER =
               new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	static SimpleDateFormat FORMATTER_KR =
               new SimpleDateFormat("yyyy년 MM월 dd일 HH시mm분ss초, E");

	private String title;
	private String originalLink;
	private String link;
	private String description;
	
	//@DateTimeFormat(pattern = "yyyy년 MM월 dd일 HH시mm분ss초")
	private String date;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginalLink() {
		return originalLink;
	}
	public void setOriginalLink(String originalLink) {
		this.originalLink = originalLink;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Article [title=" + title + ", originalLink=" + originalLink + ", link=" + link + ", description="
				+ description + ", date=" + date + "]";
	}
	
	
	
	

}
