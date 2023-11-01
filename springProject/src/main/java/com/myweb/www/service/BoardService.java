package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;

public interface BoardService {

//	int register(BoardVO bvo);

//	List<BoardVO> getList(BoardVO bvo);

	BoardVO getDetail(int bno);

	

	int update(BoardVO bvo);

	int remove(int bno);

	List<BoardVO> getList(PagingVO pagingVO);

//	int getTotal();

	int getTotal(PagingVO pagingVO);

	int insert(BoardDTO boardDTO);



	//BoardDTO getDetailImage(BoardVO bvo, List<FileVO> flist);



	BoardDTO getDetailImage(int bno);



	int fileRemove(String uuid);



	int update(BoardDTO boardDTO);



	int getCommentCount();



	int getFileCount();



	int remove2(int bno); 



	int remove3(int bno);









}
