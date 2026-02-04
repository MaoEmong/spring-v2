package com.example.boardv1.board;

import lombok.Data;

public class BoardResponse {

    @Data
    // DTO 작성 시 primary 키는 화면에 보이지 않더라도 같이 보낸다
    public static class DetailDTO {
        // 화면에 보이지 않는것
        private int id;
        private int userId;

        // 화면에 보이는것
        private String title;
        private String content;
        private String username;

        // 연산으로 만들어야 하는것
        private boolean isOwner; // 게시글의 주인인가?

        public DetailDTO(Board board, Integer sessionUserId) {
            this.id = board.getId();
            this.userId = board.getUser().getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();
            this.isOwner = (sessionUserId != null && this.userId == sessionUserId);
        }
    }

}
