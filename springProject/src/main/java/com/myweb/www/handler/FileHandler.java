package com.myweb.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myweb.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component
public class FileHandler {
	private final String UP_DIR = "D:\\_myweb\\_java\\fileupload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		//파일 경로 , fvo set, 파일 저장... , 
		List<FileVO> flist = new ArrayList<FileVO>();
		
		//파일 경로 생성 / 날짜를 폴더로 생성하여 그날 그날 업로드 파일을 관리
		LocalDate date = LocalDate.now(); // localDate 객체 오늘 날짜
		// String 변환
		String today = date.toString();// 2023-10-24
		today = today.replace("-", File.separator); // 2023\10\24(window) 2023/10/24(리눅스.,맥) 변환
		
		// D:\\_myweb\\_java\\fileupload\\2023\\10\\24
		File folders = new File(UP_DIR,today);
		//폴더값 있는지 확인
		//폴더 생성
		if(!folders.exists()) {
			//하위폴더 생성 
			folders.mkdirs();
		}
		// -- 폴더생성 끝 -- 
		
		// files 객체에 대한 설정 /fvo set
		for(MultipartFile file : files) { //들어온 첨부파일중 1개씩 for문 처리
			FileVO fvo = new FileVO();
			//전체경로는 기본설정되어있음.
			fvo.setSaveDir(today);
			fvo.setFileSize(file.getSize());
			String originalFileName = file.getOriginalFilename(); // 실제 파일네임 EX) cat1.jpg
			// originalFileName : 기본
			// 이름이 아닐수도있음.
			String fileName =originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
			fvo.setFileName(fileName);
			// 랜덤 uuid 생성
			UUID uuid = UUID.randomUUID();
			fvo.setUuid(uuid.toString());
			// -- 기본 FileVO 생성 완료 -- 
			
			// 하단부터 디스크에 저장할 파일 객체 생성
			// 파일 이름 uuid_fileName / 썸네일 : uuid_th_fileName
			String fullFileName = uuid.toString()+"_"+fileName;
			File storeFile = new File(folders, fullFileName);
			// file 객체가 저장이 되려면 첫 경로부터 다 설정이 되어 있어야 함.
			// folders : D:\\_myweb\\_java\\fileupload\
			// D:\\_myweb\\_java\\fileupload\\2023\\10\\24\\uuid_fileName.jpg
			
			try {
				file.transferTo(storeFile); // 저장 완료
				// 썸네일 생성 => 이미지 파일만 썸네일 생성
				// 이미지 파일인지 확인
				if(isImageFile(storeFile)) {
					fvo.setFileType(1);
					//썸네일 생성
					File thumbNail = new File(folders,uuid.toString()+"_th_"+fileName);
					// 파일 사이즈 설정 > 경로 
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
				
			} catch (Exception e) {
				log.debug(">>>>>>>> FILE 생성 오류 !!");
				e.printStackTrace();
			}
			//flist 에 fvo 추가
			flist.add(fvo);
		}
	
		return flist;
	}
	private boolean isImageFile(File storeFile) throws IOException { // 없을수도 있어서 throw 해줘야함
		String mimeType = new Tika().detect(storeFile); // image / jpg
		return mimeType.startsWith("image")? true:false;
	}
}
