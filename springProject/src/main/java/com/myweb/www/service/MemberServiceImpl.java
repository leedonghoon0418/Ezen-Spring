package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.repository.MemberDAO;
import com.myweb.www.security.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO mdao;

	@Transactional
	@Override
	public int register(MemberVO mvo) {
		int isOK = mdao.register(mvo);
		// 권한 부여 
		return mdao.insertAuthInit(mvo.getEmail());
	}

	@Override
	public boolean updateLastLogin(String authEmail) {
		// 
		
		return mdao.updateLastLogin(authEmail) > 0 ? true:false;
	}

	@Override
	public MemberVO getDetail(String email) {
		// TODO Auto-generated method stub
		return mdao.getDetail(email);
	}

	@Transactional
	@Override
	public List<MemberVO> getList() {
		List<MemberVO> list = mdao.getList();
		
		for(MemberVO mvo : list) {
			mvo.setAuthList(mdao.selectAuths(mvo.getEmail()));
		}
		
		return list;
	}

	@Override
	public int modify(MemberVO mvo) {
		// TODO Auto-generated method stub
		return mdao.modify(mvo);
	}

	@Override
	public int remove(String email) {
		//권한 삭제
		mdao.authRemove(email);

		
		return mdao.remove(email);
	}
}
