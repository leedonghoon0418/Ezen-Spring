package com.myweb.www.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.security.MemberVO;
import com.myweb.www.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/**")
@Slf4j
public class MemberController {
	
	@Inject
	private BCryptPasswordEncoder bcEncoder;
	
	@Inject
	private MemberService msv;
	
	@GetMapping("/register")
	public String getRegister() {
		
		return "/member/register";
	}
	
	@PostMapping("/register")
	public String postRegister(MemberVO mvo) {
		mvo.setPwd(bcEncoder.encode(mvo.getPwd())); // 값 암호화
		int isOk = msv.register(mvo);
		return "index";
	}
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String loginPost(HttpServletRequest request, RedirectAttributes re) {
		// 로그인이 잘못 되었을 때 다시 로그인 페이지로 돌아와 오류 메시지 전송
		// 다시 로그인 유도
		re.addAttribute("email",request.getAttribute("email"));
		re.addAttribute("errMsg",request.getAttribute("errMsg"));
		return "redirect:/member/login";
	}
	
	@GetMapping("/detail")
	public String memberDetail(RedirectAttributes re, @RequestParam("email") String email, Model m) {
		MemberVO mvo = msv.getDetail(email);
		m.addAttribute("mvo",mvo);
		
		
		return "/member/detail";
	}
	
	@GetMapping("/list")
	public String memberList(Model m){
		List<MemberVO> mvo = msv.getList();
		
		m.addAttribute("mvo",mvo);	
		return "/member/list";
	}
	
	@GetMapping("/modify")
	public String modify(Model m, @RequestParam("email") String email) {
		MemberVO mvo = msv.getDetail(email);
		m.addAttribute("mvo",mvo);
		
		return "/member/modify";
	}
	
	@PostMapping("/modify")
	public String getModify(MemberVO mvo, RedirectAttributes re , HttpServletRequest request, HttpServletResponse response) {
		if(mvo.getPwd() == null || mvo.getPwd().equals("")) {
			re.addFlashAttribute("rePwd", "1");
			return "redirect:/member/modify?email="+mvo.getEmail();
		}else {
			mvo.setPwd(bcEncoder.encode(mvo.getPwd()));
			int isOk = msv.modify(mvo);	
			
		}
		// 로그아웃 타게하는거
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	         new SecurityContextLogoutHandler().logout(request, response, auth);
	
		return "index";
	}
	@GetMapping
	public String remove(@RequestParam("email") String email,HttpServletRequest request, HttpServletResponse response) {
		int isOk = msv.remove(email);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        
		return "index";
	}
	
}
