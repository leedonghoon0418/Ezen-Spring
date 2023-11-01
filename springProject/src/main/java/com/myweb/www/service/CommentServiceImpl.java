package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService{

	
	@Inject
	private CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.post(cvo);
	}

//	@Override
//	public List<CommentVO> getList(int bno) {
//		// TODO Auto-generated method stub
//		return cdao.getList(bno);
//	}

	@Override
	public int removeComment(int cno) {
		// TODO Auto-generated method stub
		return cdao.removeComment(cno);
	}

	@Override
	public int edit(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.edit(cvo);
	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// totalCount 가져오기 
		int totalCount = cdao.selectOntBnoTotalCount(bno);
		log.info(totalCount+"total");
		// Commet List 가져오기 찾아오기
		List<CommentVO> list = cdao.selectListPaging(bno,pgvo);
		log.info(list+"comment list");
		// Paging Handler 값 완성해서 return
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		
		return ph;
	}

}
