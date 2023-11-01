package com.myweb.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {
	
	private int pageNo;
	private int qty;
	// 검색 처리용
	private String type; 
	private String keyword; 
	
	
	public PagingVO() {
		this(1,10);
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo=pageNo;
		this.qty=qty;
	}
	
	// (DB에 limit = 시작페이지, qty)시작 페이지 번호 구하기
	// pageNo = 1 -> 2 -> 3 -> 4 ...
	//     0,10 / 10,10 / 20,10 ...
	public int getPageStart() {
		return (this.pageNo-1)*qty;
	}
	
	//타입의 형태를 여러가지 형태로 복합적인 검색을 하기 위해서
	// 타입의 키워드 t,c,w,tc,tw,tcw 
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
	
	
}
