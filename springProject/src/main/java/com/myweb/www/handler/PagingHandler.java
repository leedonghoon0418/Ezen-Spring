package com.myweb.www.handler;

import java.util.List;

import javax.inject.Inject;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PagingHandler {
	
	@Inject
	private PagingVO pgvo;
	
	//화면에 보여지는 시작 페이지네이션 번호
	private int startPage;
	//화면에 보여지는 끝 페이지 네이션 번호
	private int endPage;
	//이전 , 다음 버튼 존재 여부
	private boolean prev,next;
	//총 글수
	private int totalCount;
	
	private int realEndPage;
	
	
	private List<CommentVO> cmtList;
	
	//PagingVO = 현재 페이징값 가져오는 용도 / totalcount  = DB에서 조회해서 매개변수로 입력받기
	public PagingHandler(PagingVO pgvo,int totalCount) {
		// pgvo.qty : 한페이지에 보이는 게시글의 수
		// pgvo.page
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		//화면에 보여지는 페이지 네이션중 10, 20 ,30
		// 1페이지부터 10페이지까지 어떤 페이지가 선택되도 end는 10이여야 한다.
		// 1 2 3 4 5 ... 10 / 10 나머지를 올려(ceil) 1로 만들고 * 10 
		// 화면에 표시되어야 하는 페이지 개수 ( 1 2 3 4 5 6 .. 10) => 10개
		
		this.endPage =
				(int)Math.ceil(pgvo.getPageNo() / (double)pgvo.getQty())*pgvo.getQty();
		//화면에 보여지는 페이지 네이션중 1 , 11 , 21
		this.startPage = endPage-9;
		
		// 여기서는 전체 글수에서 / 한페이지에 표시되는 글수 올림
		this.realEndPage = 
				(int)Math.ceil(totalCount / (double)pgvo.getQty());
		if(realEndPage < endPage) {
			this.endPage = realEndPage;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	
	
	public PagingHandler(PagingVO pgvo, int totalCount, List<CommentVO> cmtList) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
}
