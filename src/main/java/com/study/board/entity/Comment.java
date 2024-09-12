package com.study.board.entity;

import com.study.board.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Board board){
        if (dto.getId() != null){
            throw new IllegalStateException("댓글 생성 실패! Id가 없어야함");
        }
        if (dto.getBoardId() != board.getId()){
            throw new IllegalStateException("댓글 생성 실패! 게시글의 id가 잘못됨");
        }

        return new Comment(
                dto.getId(),
                board,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto){
        if (this.id != dto.getId()){
            throw new IllegalStateException("댓글 수정 실패! 잘못된 Id 입력");
        }
        if (dto.getNickname() != null){
            this.nickname = dto.getNickname();
        }
        if (dto.getBody() != null){
            this.body = dto.getBody();
        }
    }
}
