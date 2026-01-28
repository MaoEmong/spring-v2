package com.example.boardv1.board;

import lombok.Data;

// 데이터 트랜스퍼 오브젝트
@Data
public class BoardSaveDTO {
    private String title;
    private String content;
}
