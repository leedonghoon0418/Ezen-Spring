package com.myweb.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.cxf.jaxrs.impl.PathSegmentImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myweb.www.domain.FileVO;
import com.myweb.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileSweeper {
	// 파일을 청소하는 핸들러
	// 일정 시점에서(예약시점)에서 DB와 현재 업로드 파일 폴더를 비교하여
	// DB에는 없고 upload 경로에는 존재하는 파일을 찾아서 삭제하는 기능.
	
	
	private final String BASE_PATH= "D:\\_myweb\\_java\\fileupload\\";
	
	@Inject
	private FileDAO fdao;
	
	// 초 분 시 일 월 요일 년도(생략가능)
	@Scheduled(cron = "0 0 21 * * *")
	public void fileSweeper() {
		log.info(">>FILE SWEEPER RUNNING START : { } > "+LocalDateTime.now());
		
		// DB에 등록된 파일 목록
		List<FileVO> dbList = fdao.selectListAllFiles();
		
		// 저장소 검색할 때 필요한 파일(실제 존재해야 될 리스트)
		List<String> currFiles = new ArrayList<String>();
		
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir()+"\\"+fvo.getUuid();
			String fileName = fvo.getFileName();
			// 총경로
			
			currFiles.add(BASE_PATH+filePath+"_"+fileName);
			
			// 이미지파일이면 썸네일
			if(fvo.getFileType() > 0) {
				//이미지
				currFiles.add(BASE_PATH+filePath+"_th_"+fileName);
			}
		}
		// 날짜를 반영한 폴더 구조 경로 만들기
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// 경로를 기반으로 저장되어있는 파일을 검색
		File dir = Paths.get(BASE_PATH+today).toFile();
		File[] allFileObject = dir.listFiles();
		
		// 실제 저장되있는 파일 / DB에 존재하는 파일을 비교하여 없으면 삭제 진행
		for(File file : allFileObject) {
			String storedFileName = file.toPath().toString(); // 해당 Path의 전체 이름
			if(!currFiles.contains(storedFileName)) {
				file.delete(); // 실제 파일 삭제
				log.info(">> Delete File > "+storedFileName);
			}
		}
	}
	
}
