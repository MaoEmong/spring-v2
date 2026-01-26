package com.example.boardv1.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

/*
하이버네이트 기술
*/

@RequiredArgsConstructor // Lombok / final이 붙어있는 모든 필드를 초기화하는 생성자 제공
@Repository // 컴포넌트 스캔
// BoardRepository는 EntityManager에 의존하는 의존성 코드
public class BoardRepository {
    // final : 1. 상수 선언 2. 클래스 생성 시 반드시 초기화
    private final EntityManager em;

    // DI 디펜던싱 인젝션 -> 의존성 주입 (의존하고 있는게 IoC에 떠있어야됨)
    // public BoardRepository(EntityManager em) {
    // this.em = em;
    // }

    public Board findById(int id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll() {
        // 객체지향 쿼리
        Query query = em.createQuery("select b from Board b", Board.class);
        List<Board> list = query.getResultList();
        return list;
    }

    public void save(Board board) {
        em.persist(board);
    }

    public void delete(Board board) {
        em.remove(board);
    }
}