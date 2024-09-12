package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8090/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam(name = "file", required = false) MultipartFile file) {
        try {
            boardService.write(board, file);
            model.addAttribute("message", "글 작성이 완료되었습니다");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        } catch (Exception e) {
            model.addAttribute("message", "글 작성 중 문제가 발생했습니다: " + e.getMessage());
            model.addAttribute("searchUrl", "/board/write");
            return "message";
        }
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value="searchKeyword", required = false) String searchKeyword) {

        Page<Board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model,@RequestParam("id") Integer id){

        model.addAttribute("board",boardService.boardView(id));

        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam("id") Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("board", boardService.boardView(id));


        return "boardmodify";
    }

    // 글 수정 처리
    @PostMapping("/board/update")
    public String boardUpdate(@RequestParam("id") Integer id, Board board, @RequestParam(name = "file") MultipartFile file) throws Exception{

        // 기존 게시글 조회
        Board boardTemp = boardService.boardView(id);

        // 게시글 정보 수정
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        // 파일이 있는 경우 처리
        if (file != null && !file.isEmpty()){
            // 파일 업로드 처리
            boardService.write(boardTemp, file);
        }else{
            // 파일이 없을 경우 처리
            boardService.write(boardTemp, null);
        }

        return "redirect:/board/view?id=" + boardTemp.getId();
    }
}