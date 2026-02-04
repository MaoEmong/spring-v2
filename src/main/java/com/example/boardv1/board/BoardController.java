package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.boardv1.board.BoardResponse.DetailDTO;
import com.example.boardv1.user.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // 인증(v), 권한(x)
    @PostMapping("/boards/save")
    public String save(BoardRequest.SaveOrUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다");

        boardService.게시글쓰기(reqDTO.getTitle(), reqDTO.getContent(), sessionUser);
        return "redirect:/";
    }

    // 인증(x), 권한(x)
    @GetMapping("/")
    public String index(HttpServletRequest req) {
        List<Board> list = boardService.게시글목록();
        req.setAttribute("models", list);
        return "index";
    }

    // 인증(v), 권한(x)
    @GetMapping("/boards/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다");

        return "board/save-form";
    }

    // 인증(v), 권한(v)
    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest req) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다");

        Board board = boardService.수정폼게시글정보(id, sessionUser.getId());
        req.setAttribute("model", board);
        return "board/update-form";
    }

    // 인증(v), 권한(v)
    @PostMapping("/boards/{id}/update")
    public String update(@PathVariable("id") int id, BoardRequest.SaveOrUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null)
            throw new RuntimeException("인증되지 않았습니다");

        boardService.게시글수정(id, sessionUser.getId(), reqDTO.getTitle(), reqDTO.getContent());
        return "redirect:/boards/" + id;
    }

    // 인증(x), 권한(x)
    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") int id, HttpServletRequest req) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = sessionUser == null ? null : sessionUser.getId();
        DetailDTO dto = boardService.상세보기(id, sessionUserId);
        req.setAttribute("model", dto);
        return "board/detail";
    }

    // 인증(v), 권한(v)
    @PostMapping("/boards/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("인증되지 않았습니다.");
        }
        boardService.게시글삭제(id, sessionUser.getId());
        return "redirect:/";
    }
}
