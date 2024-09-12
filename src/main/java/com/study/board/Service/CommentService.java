package com.study.board.Service;

import com.study.board.dto.CommentDto;
import com.study.board.entity.Comment;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;

    // 댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto){
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 수정 실패"+"대상 댓글 없음"));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    // 댓글 조회
    public List<CommentDto> comments(Integer boardId){
        return commentRepository.findByBoardId(boardId)
                .stream()
                .map(CommentDto::createCommentDto)
                .collect(Collectors.toList());
    }

    // 댓글 생성
    @Transactional
    public CommentDto create(Integer boardId, CommentDto dto){
        // 게시글 조회 및 예외 발생
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패!"+"게시글이 없음"));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, board);

        // 댓글 엔티티 -> DB에 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }


    // 댓글 삭제
    @Transactional
    public CommentDto delete(Long id){
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글 삭제 실패!"+"대상 없음"));

        // 댓글 삭제
        commentRepository.delete(target);

        // DTO로 변환해 반환
        return CommentDto.createCommentDto(target);
    }

}
