package org.springframework.samples.web;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.web.domain.Authenticate;
import org.springframework.samples.web.domain.User;
import org.springframework.samples.web.domain.UserSession;
import org.springframework.samples.web.service.BulletinFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import net.sf.json.JSONObject;

@Controller 
@SessionAttributes({"userSession", "RSAWebKey"}) //다른 컨트롤러에서도 사용가능 
public class AccountController {
	
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired 
	private BulletinFacade bulletinFacade; // 직접연관?
	public void setBulletinFacade( BulletinFacade bulletinFacade){
		this.bulletinFacade = bulletinFacade;
	}
	
	@RequestMapping("/")
	public String Home(){
		log.debug("index sucess");
		return "index";
	} 
	
	//로그인 폼
	@RequestMapping("/user/loginForm")
	public String loginForm(Model model) throws NoSuchAlgorithmException, InvalidKeySpecException{ //폼폼태그에서 도메인객체랑 자동매핑하기 위해서 여기에커맨드 객체 만드는 것이 필요/여기서 지정한 이름이 뷰에서 참조할 때 사용
		log.debug("loginForm success");
		
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA"); //인스턴스 생성 (1)
		generator.initialize(1024); //비트형식의 원하는 키 사이즈로 초기화 2^10 (2)
		KeyPair keyPair = generator.genKeyPair(); //한쌍의 RSA키를 생성할 수 있다.  (3) KeyPairGenerator 객체를 재사용
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		model.addAttribute("RSAWebKey", privateKey); //세션에 공개키의 문자열을 키로하여 개인키를 저장한다. 
		
		//공개키를 문자열로 변환하여 JavaScript RSA 라이브러리 넘겨준다.
		RSAPublicKeySpec publicSpec = (RSAPublicKeySpec)keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		
		String publicKeyModulus = publicSpec.getModulus().toString(16);
		String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
		
		model.addAttribute("publicKeyModulus", publicKeyModulus);
		model.addAttribute("publicKeyExponent", publicKeyExponent);
		//메소드를 따로 뺀다고 하면 3개다 map으로 받아서 return 해주기

		return "users/login";
	}
	public String decryptRsa(PrivateKey privateKey, String securedValue){
		String decryptedValue = "";
		try{
			Cipher cipher = Cipher.getInstance("RSA");
			/**
			 * 암호화 된 값을 byte 배열이다.
			 * 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다.
			 * 서버측에서도 값을 받을 때 hex 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
			 */
			byte[] encryptedBytes = hexToByteArray(securedValue);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			decryptedValue = new String(decryptedBytes, "utf-8");//문자 인코딩 주의
		}catch(Exception e){
			log.info("decryptRsa Excetion Error : " + e.getMessage());
		}
		return decryptedValue;
	}
	
	//16진 문자열을 byte 배열로 변환한다. 
	private byte[] hexToByteArray(String hex) {
		// TODO Auto-generated method stub
		if(hex == null || hex.length() % 2 != 0)
			return new byte[]{};
		
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<hex.length(); i+= 2) {
			byte value = (byte)Integer.parseInt(hex.substring(i, i+2), 16);
			bytes[(int) Math.floor(i/2)] = value;
		}
		return bytes;
	}

	//로그인 확인 
	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
	public @ResponseBody JSONObject loginCheck(@RequestBody Authenticate authenticate, HttpSession session, Model model){
		JSONObject listObj = new JSONObject();
		
		String uid = authenticate.getUserId();
		String pwd = authenticate.getPassword();
		
		//로그인 전에 세션에 저장된 개인키를 가져온다.
		PrivateKey privateKey = (PrivateKey) session.getAttribute("RSAWebKey");
		session.removeAttribute("RSAWebKey"); // 키의 재사용을 막는다. 항상 새로운 키를 받도록
		
		if(privateKey == null){
			listObj.put("state", "false");
		}
		else{
			try{
				String _uid = decryptRsa(privateKey, uid);
				String _pwd = decryptRsa(privateKey, pwd);
				
				User user = bulletinFacade.findById(_uid); //이 부분은 DB값과 비교해본다.
				
				if(!user.matchPassword2(_pwd)){
					listObj.put("matchPwd", "false");
					
				}
				//세션에 로그인한 사용자 저장
				UserSession userSession = new UserSession(user);
				model.addAttribute("userSession", userSession);
				//listObj.put("state", "true");
				
			}catch(Exception e){
					listObj.put("state", "false");
					log.info("login ERROR : "+e.getMessage());
			}
			
		}
		return listObj;
	}
	
	//로그아웃 
	@RequestMapping("/user/logout")
	public String logout(SessionStatus session){
		session.setComplete();
		return "redirect:/";
		
	}
	//회원가입도 만들기!!!!!  --> 키를 생성하는RSA 알고리즘 따로 빼기!
	//회원가입 Form
	@RequestMapping("/user/signUp") 
	public String singUpForm(Model model){ 	//model 객체가 getParameter라고 생각하면 됨 
		log.debug("signUp");
		model.addAttribute("user", new User()); 
		return "users/signUpForm";
		
	}
	
	//회원가입으로 data를 DB에 추가 
	@RequestMapping(value="/users/form", method=RequestMethod.POST) //POST방식으로 하면 명시해줘야함! GET방식은 안해줘도 됨 
	public String createForm(@Valid User user, BindingResult bindingResult) { //유효성 검사를 해서 bindingResult에 담아준다. 
		log.debug("zz User : {}", user);
		
		if( bindingResult.hasErrors()) {
			log.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors(); //어떤 에러가 발생했는지 볼 수 있음 
			for(ObjectError error : errors) {
				log.debug("error : {}, {} ",error.getCode(), error.getDefaultMessage()); 
			}
			return "users/signUpForm";
		}
		bulletinFacade.create(user); // 연관이 맞다  --> Aggregation

		return "redirect:/"; //정상적으로 되면 루트화면(첫 화면)으로 돌아가게 한다.
	}
	
	//개인정보 수정 폼 
	@RequestMapping("/user/{userId}/signUp") 
	public String updateForm(@PathVariable String userId, Model model){ //model객체 - 기본적으로 인터페이스를 구현하지 않았음에도 Map과 같은 형태로 기능이 작동하는 객체
		if(userId == null){
			throw new IllegalArgumentException("사용자 아이디가 필요합니다.");
		}
		
		User user = bulletinFacade.findById(userId);
		
		model.addAttribute("user", user); 
		return "users/signUpForm";
		
	}
	
	
	//개인정보 수정 업데이트 
	@SuppressWarnings("unused")
	@RequestMapping(value="/users/form", method=RequestMethod.PUT) //
	public String update(@Valid User user, BindingResult bindingResult, Model model, HttpSession session) { //유효성 검사를 해서 bindingResult에 담아준다. 
		log.debug("User : {}", user);
		if( bindingResult.hasErrors()) {
			log.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors(); //어떤 에러가 발생했는지 볼 수 있음 
			for(ObjectError error : errors) {
				log.debug("error : {}, {} ",error.getCode(), error.getDefaultMessage()); 
			}
			return "users/signUpForm";
		}
		
		//세션에서 값 꺼내오기
		UserSession temp = (UserSession)session.getAttribute("userSession");
		log.debug("{}" ,temp.toString());
		if(temp == null){
			throw new NullPointerException();
		}

		bulletinFacade.update(user); //로직 처리 
		log.debug("Database: {}", bulletinFacade.findById(user.getUserId()));
		return "redirect:/"; //정상적으로 되면 루트화면(첫 화면)으로 돌아가게 한다.
	}
	
}
