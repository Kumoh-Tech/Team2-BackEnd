package com.study.board.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void write(Board board, MultipartFile file) throws Exception {
        if (file != null && !file.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "/src/main/webapp/";
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            Path savePath = Paths.get(projectPath, fileName);

            // 디렉토리 존재 여부 확인 및 생성
            if (!Files.exists(savePath.getParent())) {
                Files.createDirectories(savePath.getParent());
            }

            try {
                Files.copy(file.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new Exception("파일 업로드 실패", e);
            }

            board.setFilename(fileName);
            board.setFilepath("/webapp/" + fileName);
        }

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Board boardView(Integer id){

        return boardRepository.findById(id).orElse(null);
    }

    public void boardDelete(Integer id){

        boardRepository.deleteById(id);
    }
}