package com.example.boardv1.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        Board board = new Board();
        board.setTitle("title7");
        board.setContent("content7");
        System.out.println("===before persist");
        System.out.println(board);

        // when
        boardRepository.save(board);

        // eye (board객체가 DB데이터와 동기화 됨)
        System.out.println("===after persist");
        System.out.println(board);
    }

    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Board board = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("username을 찾을 수 가 없어요"));
        // boardRepository.findById(1);

        // eye
        System.out.println(board);
    }

    @Test
    public void findByIdV2_test() {
        // given
        int id = 1;

        // when
        boardRepository.findById(id);
        em.clear();
        boardRepository.findById(id);
    }

    @Test
    public void findAll_test() {
        // given

        // when
        var list = boardRepository.findAll();

        // eye
        for (Board board : list) {
            System.out.println(board);
        }
    }

    @Test
    public void delete_test() {
        // given
        Board board = boardRepository.findById(1).orElseThrow(() -> new RuntimeException("username을 찾을 수 가 없어요"));
        // when
        boardRepository.delete(board);
        // eye
        em.flush();
    }

    @Test
    public void update_test() {
        // given
        Board board = boardRepository.findById(1).orElseThrow(() -> new RuntimeException("username을 찾을 수 가 없어요"));

        // when
        board.setTitle("title1-update");

        // eye
        em.flush();
    }
}
