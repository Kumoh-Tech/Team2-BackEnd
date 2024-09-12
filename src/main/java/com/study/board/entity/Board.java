package com.study.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String filename;

    @Column
    private String filepath;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;

}