package com.study.board.repository;

import com.study.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    // @Query(value = "SELECT * FROM comment WHERE board_id = :boardId", nativeQuery = true)
    List<Comment> findByBoardId(Integer boardId);

    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);

}
