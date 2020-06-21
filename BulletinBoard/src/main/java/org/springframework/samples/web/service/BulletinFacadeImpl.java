package org.springframework.samples.web.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.web.dao.mybatis.BoardDao;
import org.springframework.samples.web.dao.mybatis.UserDao;
import org.springframework.samples.web.domain.Article;
import org.springframework.samples.web.domain.Board;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.domain.Reply;
import org.springframework.samples.web.domain.User;
import org.springframework.stereotype.Service;


@Service //해줘야 여기를 인식한다!
public class BulletinFacadeImpl implements BulletinFacade {
	
	//이거 감추기!!
	private static String clientID = "bVmMpzHooTCT9WHMu2mP";
	private static String clientSecret = "F5xr_G8SiC";
	
	
	//business/service object는 interface를 통해 DAO접근  ->서비스 객체의 단위 테스트 가능
	@Autowired
	private UserDao userDao;
	@Autowired
	private BoardDao boardDao;
	
	
	@Override
	public User findById(String userId) {
		return userDao.findById(userId);
	}

	@Override
	public void create(User user) {
		userDao.create(user);

	}
	
	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void create(Board board) {
		boardDao.create(board);
	}

	@Override
	public void delete(Board board) {
		boardDao.delete(board);
	}

	@Override
	public void update(Board board) {
		boardDao.update(board);

	}

	@Override
	public Board findByNo(int no) {
		return boardDao.findByNo(no);
	}

	@Override
	public int boardCount() {
		// TODO Auto-generated method stub
		System.out.println("count");
		return boardDao.listCount();
	}

	@Override
	public List<Board> boardList(int page, int count, BoardPager boardPager){
		// TODO Auto-generated method stub
		
		countCal(page, count, boardPager);
		boardPager.setStart(boardPager.getStart()-1);
		return boardDao.boardList(boardPager);
	}
	
	public void countCal(int page, int count, BoardPager boardPager) {
		//한 페이지 블록에 나올 페이지 갯수
		//현재 페이지 기준으로 끝 페이지 계산 page = 1~5이면 끝 페이지는 5! 6~10이면 10!!
		int tempEnd = (int)((Math.ceil(page/10.0)*10));//5, 10, 15 ... 
		
		int tempStart = tempEnd -9; //0, 5, 10 ...
		System.out.println("tempEnd" + tempEnd);
		System.out.println("tempStart" + tempStart);
		System.out.println("count" + count);

		//실제로 나올 페이지 계산
		int actualPageCnt = (int) Math.ceil(count/10.0);
		
		
		if(tempStart != 1)
			boardPager.setPrev(true);
	
		if(tempEnd < actualPageCnt){
			boardPager.setNext(true);
		}
		
		if(tempEnd > actualPageCnt)
			boardPager.setTempEnd((tempStart-1) + actualPageCnt%10);
		
		else
			boardPager.setTempEnd(tempEnd);
		
		boardPager.setTempStart(tempStart);
		boardPager.setStart((page-1)*10 + 1);//0부터 가져와야하므로 page-1을 해줘야한다.
		boardPager.setEnd(10);
		
	}
	public void insert(Reply reply){
		boardDao.insert(reply);
	}
	
	@Override
	public void replyDelete(int replyNo) {
		// TODO Auto-generated method stub
		boardDao.replyDelete(replyNo);
	}

	@Override
	public List<Reply> replyList(int boardNo) {
		// TODO Auto-generated method stub
		return boardDao.replyList(boardNo);
	}

	@Override
	public Reply replyOne(int boardNo) {
		// TODO Auto-generated method stub
		return boardDao.replyOne(boardNo);
	}

	@Override
	public List<Article> articleList(String keyword, int page, BoardPager pager) { //검색결과로 제공하는 URL은 일정 시간동안만 유효하다. --> 검색 API 호출결과를 저장 및 재가공하는 것 원칙적으로 허용 불가
		// TODO Auto-generated method stub
		List<Article> list = new ArrayList<Article>();
		int count = 1000; // 네이버에서 제공하는 최대 갯수
		countCal(page, count, pager); //count는 리스트 전체 갯수!!
	
		Article article = null;
		
		try{
			URL url;
			
			url = new URL("https://openapi.naver.com/v1/search/news.json?query=" 
					+ URLEncoder.encode(keyword, "UTF-8")
					+ (pager.getEnd() != 0 ? "&display=" + pager.getEnd() :"" )
					+ (pager.getStart() != 0 ? "&start=" + pager.getStart() :""));
			//System.out.println(url);
			//URL클래스에서 connection 설정
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			
			urlConn.setRequestProperty("X-Naver-Client-Id",clientID);
			urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream())); // URLConnection 객체로부터 입력스트림 구함 -> 문자 데이터를 읽기에 편리한 InputStreamReader로 감싼다. -> 다시 효과적으로 읽기 위해 버퍼화 된 입력스트림으로 싸서 최종적인 입력스트림 만들기
			
			String msg = null;
			StringBuffer response = new StringBuffer(); //변하는 문자열을 다룬다. 객체의 크기 동적
			
	        while((msg = br.readLine())!=null) // 현재 스트림에서 한 줄의 문자를 읽고 데이터를 문자열로 반환
	        {
	            System.out.println(msg);
	        	response.append(msg); // 문자열 데이터를 현재 문자열 끝에 추가한다.
	        }
	        br.close();
	        
	        //JSON으로 파싱하기
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(response.toString()); // response를 스트링형태로 바꾸고 JSON Oject로 만들어 준다.  
			JSONArray articleArray = (JSONArray)jsonObject.get("items"); //items의 배열 추출
			
			for(int i=0; i<articleArray.size(); i++){
				article = new Article();
				
				JSONObject articleObject = (JSONObject) articleArray.get(i); // json형태는 {}형태이므로 {에서 시작해 } 로 끝나는 것이 하나씩 들어간다. 
				//json형태는 , 로 값을 구별 
				article.setOriginalLink((String)articleObject.get("originallink")); // : 뒤에 있는 것이 해당 data
				article.setLink((String)articleObject.get("link"));
				article.setDescription((String)articleObject.get("description"));
				article.setTitle((String)articleObject.get("title"));
				article.setDate((String)articleObject.get("pubDate"));
				
				list.add(article);
			}
	        urlConn.disconnect();
	        
		}catch(MalformedURLException e){
			System.out.println("The URL address is incorrect.");
			e.printStackTrace();
		}catch(IOException e){
			System.out.println("It can't connect to the web page.");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


}
