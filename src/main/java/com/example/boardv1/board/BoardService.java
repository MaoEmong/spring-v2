package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.boardv1.user.User;

import lombok.RequiredArgsConstructor;

// Service의 책임 : 트랜잭션의 관리, DTO만들기, 권한 체크(DB정보가 필요하니까)
// Service는 Repository에 의존
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> 게시글목록() {
        return boardRepository.findAll();
    }

    public BoardResponse.DetailDTO 상세보기(int id, Integer sessionUserId) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수가 없어요"));

        return new BoardResponse.DetailDTO(board, sessionUserId);
    }

    @Transactional // update, delete, insert할 때 붙이기
    public void 게시글수정(int id, int sessionUserId, String title, String content) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없어요"));

        if (sessionUserId != board.getUser().getId()) {
            throw new RuntimeException("수정 권한이 없습니다");
        }

        board.setTitle(title);
        board.setContent(content);
    }

    // 원자성(모든게 다되면 commit, 하나라도 실패하면 rollback)
    // 트랜잭션 종료시 자동 flush 동작
    @Transactional
    public void 게시글쓰기(String title, String content, User sessionUser) {
        // 비영속 객체
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setUser(sessionUser);

        // persist
        boardRepository.save(board);
    }

    @Transactional
    public void 게시글삭제(int id, int sessionUserId) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 글을 찾을 수 없어요"));

        if (sessionUserId != board.getUser().getId()) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }

        boardRepository.delete(board);
    }

    public Board 수정폼게시글정보(int id, int sessionUserId) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수가 없어요"));

        if (sessionUserId != board.getUser().getId()) {
            throw new RuntimeException("수정 권한이 없습니다");
        }

        return board;
    }
}
