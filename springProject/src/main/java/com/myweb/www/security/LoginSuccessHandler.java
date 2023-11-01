package com.myweb.www.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.myweb.www.service.MemberService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	
	@Getter
	@Setter
	private String authEmail;
	
	@Getter
	@Setter
	private String authUrl;
	
	// redirect로 데이터를 가져가는 역할 (RedirectStrategy)
	private RedirectStrategy rdstg = new DefaultRedirectStrategy();
	
	// 실제 로그인 정보, 경로 등을 저장해서 rdstg가 가지고감
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Inject
	private MemberService msv;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		setAuthEmail(authentication.getName()); // 권한을 가지는 객체의 이메일의 네임
		setAuthUrl("/board/list"); // 성공후 경로 
		
		boolean isOk = msv.updateLastLogin(getAuthEmail()); // 마지막 로그인 정보 띄우기
		// 내부에서 로그인 세션 저장됨.
		
		//이미 세션에 정보가 저장되어있음.
		HttpSession ses = request.getSession();
		log.info("login Success" + ses.toString());
		
		//시큐리티에서 로그인 값이 없다면 null로 저장될 수 있음.
		if(!isOk || ses == null) {
			return;
		}else {
			// 시큐리티에서 로그인을 시도하면 시도한 로그인에 대한 로그인 기록 남게됨.
			// 이전 시도한 시큐리티의 로그인 인증 실패 기록이 남아있을수 있음.
			// 성공했다면 해당 기록을 삭제해줘야함.
			
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // 인증 실패에 대한 익셉션 기록을 리무브			
		}
		SavedRequest saveReq = reqCache.getRequest(request, response);
		rdstg.sendRedirect(request, response, (saveReq != null ? saveReq.getRedirectUrl() : getAuthUrl()));
	}

}
