package com.study.board.Service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/webapp/" + fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList(){

        return boardRepository.findAll();
    }

    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }
}