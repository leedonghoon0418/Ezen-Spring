package com.myweb.www.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	//보드의 내용과 첨부파일을 하나로 묶기위해 DTO 사용
	
	private BoardVO bvo;
	private List<FileVO> flist;
}
