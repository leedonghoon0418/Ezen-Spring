package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

//	List<BoardVO> getList(BoardVO bvo);

	BoardVO getDetail(int bno);

	int update(BoardVO bvo);

	int remove(int bno);

	void getReadCount(int bno);

	List<BoardVO> getList(PagingVO pagingVO);

	int getTotal(PagingVO pagingVO);

	long selectOneBno();

	int insertDetail();



}
