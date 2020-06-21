package org.springframework.samples.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.web.domain.Article;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.service.BulletinFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired 
	private BulletinFacade bulletinFacade; 
	
	public void setBulletinFacade( BulletinFacade bulletinFacade){
		this.bulletinFacade = bulletinFacade;
	}
	
	@RequestMapping("/newsList")
	public String RealTimeNewsList(BoardPager pager,
					@RequestParam("pageBlock") int pageBlock, Model model) throws Exception{ //콘솔창에 찍어보는 거니까 리턴값을 임의적으로 void
		System.out.println("pageBlock : " + pageBlock);
		
		List<Article> list = bulletinFacade.articleList("지구 환경 기후", pageBlock, pager);
		model.addAttribute("newsList", list);
		model.addAttribute("newsPager", pager);
		
		System.out.println(pager.toString());
		
		return "realTimeNewsList";
	}
	
	//이거는 삭제 
	@RequestMapping("/newsDescription")
	public String articleContent(@RequestParam("newsUrl") String url, Model model){
		System.out.println("url : " + url);
		return null;
	}
	
	//시군구별 실시간 평균 정보 조회 

}
