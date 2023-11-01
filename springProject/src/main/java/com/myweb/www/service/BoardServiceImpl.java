package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.repository.BoardDAO;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO bdao;
	
	@Inject
	private FileDAO fdao;
	
	@Inject
	private CommentDAO cdao;

//	@Override
//	public int register(BoardVO bvo) {
//		
//		return bdao.register(bvo);
//	}

//	@Override
//	public List<BoardVO> getList(BoardVO bvo) {
//		// TODO Auto-generated method stub
//		return bdao.getList(bvo);
//	}

	@Override
	public BoardVO getDetail(int bno) {
		bdao.getReadCount(bno);	
		return bdao.getDetail(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		// TODO Auto-generated method stub
		return bdao.update(bvo);
	}

	@Override
	public int remove(int bno) {
		// TODO Auto-generated method stub
		return bdao.remove(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		
		return bdao.getList(pagingVO);
	}



	@Override
	public int getTotal(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return bdao.getTotal(pagingVO);
	}

	@Override
	public int insert(BoardDTO bdto) {
		// bvo, flist 가져와서 각자 DB에 저장
		// 기존메서드 활용
		int isUp = bdao.insert(bdto.getBvo()); // insert를 해야 bno 발급이됨
		// null 처리
		if(bdto.getFlist() == null) {
			isUp *= 1;
			return isUp;
		}
		
		// bvo insert 후 => 파일도 있다면 .. 
		if(isUp > 0 && bdto.getFlist().size()>0) {
			long bno = bdao.selectOneBno(); // 가장 마지막에 등록된 bno 가져오기
			
			//모든 파일에 bno 세팅
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isUp *= fdao.insertFile(fvo);
			}
			
		}
		
		return isUp;
	}

	@Override
	public BoardDTO getDetailImage(int bno) {
		BoardVO bvo = bdao.getDetail(bno);
		List<FileVO> flist = fdao.getFileList(bno);
		return new BoardDTO(bvo,flist);
	}

	@Override
	public int fileRemove(String uuid) {
		
		return fdao.fileRemove(uuid);
	}

	@Override
	public int update(BoardDTO bdto) {
		// bvo, flist 가져와서 각자 DB에 저장
		// 기존메서드 활용
		
		int isUp = bdao.update(bdto.getBvo()); // insert를 해야 bno 발급이됨
		// null 처리
		if(bdto.getFlist() == null) {
			isUp *= 1;
			return isUp;
		}
				
		// bvo update 후 => 파일도 있다면 .. 
		if(isUp > 0 && bdto.getFlist().size()>0) {
		long bno = bdto.getBvo().getBno();  
					
		//모든 파일에 bno 세팅
		for(FileVO fvo : bdto.getFlist()) {
			fvo.setBno(bno);
			isUp *= fdao.updateFile(fvo);
			}
					
		}
				
		return isUp;
	}

	@Override
	public int getCommentCount() {
		// TODO Auto-generated method stub
		return cdao.getCommentCount();
	}

	@Override
	public int getFileCount() {
		// TODO Auto-generated method stub
		return fdao.getFileCount();
	}

	@Override
	public int remove2(int bno) {
		// TODO Auto-generated method stub
		return fdao.removeDetailFile(bno);
	}

	@Override
	public int remove3(int bno) {
		// TODO Auto-generated method stub
		return cdao.removeDetailComment(bno);
	}

	

	

	


}
