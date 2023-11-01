package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.FileVO;

public interface FileDAO {

	int insertFile(FileVO fvo);

	List<FileVO> getFileList(int bno);

	int fileRemove(String uuid);

	int updateFile(FileVO fvo);

	int hasFile();

	int getFileCount();

	int removeDetailFile(int bno);

	List<FileVO> selectListAllFiles();

}
