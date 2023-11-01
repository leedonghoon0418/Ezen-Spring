package com.myweb.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/comment/*")
@Slf4j
@RestController
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	// ResponseEntity 객체 사용
	// @RequestBody : body값 추출
	// value = "mappling name" / consumes : 가져오는 데이터의 형태 / produces : 보내는 데이터의 형태 나가는 데이터 타입 : MediaType. ~
	// json : application/json   text : text_plain
	
	
	@PostMapping(value= "/post", consumes="application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> postComment(@RequestBody CommentVO cvo){
		log.info("cvo >>>>>>>>>>>>>>>>>"+cvo);
		int isOk = csv.post(cvo);
		
		return isOk > 0 ? new ResponseEntity<String>("1",HttpStatus.OK) :
			new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> getList(@PathVariable("bno")long bno, @PathVariable("page")int page){
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  bno / page =="+bno+"     /     "+page);
		PagingVO pgvo = new PagingVO(page,5);
		
		
		return new ResponseEntity<PagingHandler>(csv.getList(bno,pgvo),HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> remove(@PathVariable("cno")int cno){
		int isOk = csv.removeComment(cno);
		
		return isOk > 0 ? new ResponseEntity<String>("1",HttpStatus.OK):
			new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PutMapping(value="/{cno}", consumes="application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> editComment(@RequestBody CommentVO cvo){
		
		int isOk = csv.edit(cvo);
		
		return isOk > 0 ? new ResponseEntity<String>("1",HttpStatus.OK) :
			new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
}
