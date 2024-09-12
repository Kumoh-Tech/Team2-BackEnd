package com.study.board.api;

import com.study.board.Service.CommentService;
import com.study.board.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 댓글 조회
    @GetMapping("/api/boards/{boardId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Integer boardId){
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(boardId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/boards/{boardId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Integer boardId, @RequestBody CommentDto dto){
        CommentDto createDto = commentService.create(boardId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    // 댓글 수정
    @PatchMapping("/api/boards/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,  @RequestBody CommentDto dto){
        CommentDto updateDto = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deleteDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}

