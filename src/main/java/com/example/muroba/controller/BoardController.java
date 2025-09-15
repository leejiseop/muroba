package com.example.muroba.controller;

import com.example.muroba.entity.Board;
import com.example.muroba.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") // localhost:8090/board/write
    public String boardWriteForm() {
        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam(name="file", required = false) MultipartFile file) throws Exception {

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable,
                            String searchKeyword) {

        Page<Board> list = null;

        if (searchKeyword == null) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; // = pageable.getPageNumber()
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "boardList";
    }

    @GetMapping("/board/view") // localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));


        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    // QueryString과 PathVariable의 차이 ! ! !
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable Integer id, Model model) {
        Board temp = boardService.boardView(id);

        model.addAttribute("board", temp);

        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable Integer id, Board board, Model model, @RequestParam(name="file", required = false) MultipartFile file) throws Exception {

        // 원래는 이렇게 덮어씌우면 안되고, @Transaction 과 dirty checking 을 사용해야한다
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
