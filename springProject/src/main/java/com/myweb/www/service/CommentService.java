package com.myweb.www.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;

public interface CommentService {

	int post(CommentVO cvo);

//	List<CommentVO> getList(int bno);

	int removeComment(int cno);

	int edit(CommentVO cvo);

	PagingHandler getList(long bno, PagingVO pgvo);

}
