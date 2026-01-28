package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

// Service는 Repository에 의존
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> 게시글목록() {
        return boardRepository.findAll();
    }

    public Board 상세보기(int id) {
        return boardRepository.findById(id);
    }

    @Transactional // update, delete, insert할 때 붙이기
    public void 게시글수정(int id, String title, String content) {
        Board board = boardRepository.findById(id);
        board.setTitle(title);
        board.setContent(content);
    }

    // 원자성(모든게 다되면 commit, 하나라도 실패하면 rollback)
    // 트랜잭션 종료시 자동 flush 동작
    @Transactional
    public void 게시글쓰기(String title, String content) {
        // 비영속 객체
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);

        // persist
        boardRepository.save(board);
    }

    @Transactional
    public void 게시글삭제(int id) {
        Board board = boardRepository.findById(id); // 영속화

        if (board != null)
            boardRepository.delete(board);
    }
}
