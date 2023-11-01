package com.myweb.www.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.FileHandler;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board/*")
public class BoardController {
	// 현재 폴더명 : board / RequestMapping : board
	// Mapping = /board/register
	// 목적지 => /board/register 생략 가능.
	//  
	
	
	@Inject
	private BoardService bsv;
	
	@Inject
	private FileHandler fh;
	
	@GetMapping("/register")
	public void register() {
		log.info("Start");
	}
	
	@PostMapping("/register")
	public String register(BoardVO bvo , @RequestParam(name="files", required = false)MultipartFile[] files) { // name = register.jsp에서 files 네임
		
		
		
		log.info("bvo/files"+bvo+"/"+files);
		List<FileVO> flist = null;
		// 파일 업로드 할수 있는 핸들러 생성
		if(files[0].getSize() > 0) {
			flist = fh.uploadFiles(files);
		}
		
		int isOk = bsv.insert(new BoardDTO(bvo,flist));
		
		
		
		//int isOk = bsv.register(bvo);
		//log.info((isOk>0)?"O":"X");
		return "index";
	}
	//paging 추가
	@GetMapping("/list")
	public String boardList(Model m, PagingVO pagingVO){
		int cmtQty = bsv.getCommentCount();
		int hasFile = bsv.getFileCount();
		
		log.info("pagingVO=>"+pagingVO);

		m.addAttribute("list",bsv.getList(pagingVO));
		
		//페이징 처리
		//토탈카운트 찾기 , 검색 포함.
		int totalCount = bsv.getTotal(pagingVO);
		PagingHandler ph = new PagingHandler(pagingVO, totalCount);
		m.addAttribute("ph",ph);
		
		
		
		
		return "/board/list";
	}
	
//	@GetMapping("/list")
//	public String boardList(BoardVO bvo, Model m){	
//		List<BoardVO> list = bsv.getList(bvo);
//		m.addAttribute("list",list);
//		return "/board/list";
//	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam("bno")int bno, Model m) {	
		BoardDTO bdto = bsv.getDetailImage(bno);
		m.addAttribute("bvo",bdto.getBvo());
		m.addAttribute("flist",bdto.getFlist());
		return "/board/detail";
	}
	
	
	@GetMapping("/modify")
	public void modify(@RequestParam("bno")int bno, Model m) {
		BoardVO bvo = bsv.getDetail(bno);
		BoardDTO bdto = bsv.getDetailImage(bno);
		m.addAttribute("bvo",bdto.getBvo());
		m.addAttribute("flist",bdto.getFlist());
		log.info("bvo >>"+bvo);
	}
	
	@Transactional
	@PostMapping("/modify")
	public String modify(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		
//		int isOk = bsv.update(bvo);
		log.info("bvo/files"+bvo+"/"+files);
		List<FileVO> flist = null;
		
		// 파일 업로드 할수 있는 핸들러 생성
		if(files[0].getSize() > 0) {
			// 파일 존재 O / 파일생성
			flist = fh.uploadFiles(files);
		}
		
		
		int isOk = bsv.update(new BoardDTO(bvo,flist));
		log.info((isOk>0)?"O":"X");
		return "redirect:/board/list";
	}
	
	@GetMapping("remove")
	public String remove(@RequestParam("bno")int bno) {
		int isOk = bsv.remove(bno);
		int isOk2 = bsv.remove2(bno); // 파일삭제
		int isOk3 = bsv.remove3(bno); // 댓글삭제
		
		
		
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value = "/file/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE )
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid) {
		int isOk = bsv.fileRemove(uuid);
		
		return isOk > 0 ? new ResponseEntity<String>("1",HttpStatus.OK):
			new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	//보드 삭제시 해당 comment , file 전부 삭제
	
	
	
	
	
}
