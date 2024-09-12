package com.study.board.dto;

import com.study.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    private Integer boardId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getBoard().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
