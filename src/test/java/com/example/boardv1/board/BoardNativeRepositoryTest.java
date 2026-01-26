package com.example.boardv1.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

// 단위 테스트

@Import(BoardNativeRepository.class) // BoardNativeRepository가 IoC에 등록됨
@DataJpaTest // EntityManager가 IoC에 등록됨
public class BoardNativeRepositoryTest {

    @Autowired // 어노테이션 DI 기법
    private BoardNativeRepository boardNativeRepository;

    @Test
    public void findById_test() {
        // given
        int id = 1;
        // when -> 행위 검증
        var board = boardNativeRepository.findById(id);
        // eye -> 상태 검증
        System.out.println(board);
    }

    @Test
    public void findAll_test() {
        // given
        // when
        List<Board> list = boardNativeRepository.findAll();
        // eye
        for (Board board : list)
            System.out.println(board);
    }

    @Test
    public void save_test() {
        // given
        String title = "title7";
        String content = "content7";
        // when
        boardNativeRepository.save(title, content);
        // eye
        findAll_test();

    }

    @Test
    public void deleteById_test() {
        // given
        int id = 3;
        // when
        boardNativeRepository.deleteById(id);
        // eye
        findAll_test();
    }

    @Test
    public void updateById_test() {
        // given
        int id = 3;
        String title = "title77";
        String content = "content77";
        // when
        boardNativeRepository.updateById(id, title, content);
        // eye
        findAll_test();
    }

}
