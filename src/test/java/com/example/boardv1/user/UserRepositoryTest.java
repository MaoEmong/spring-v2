package com.example.boardv1.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_fail_test() {
        // given
        User user = new User(); // 비영속 객체
        user.setUsername("cos");
        user.setPassword("1234");
        user.setEmail("cos@nate.com");

        // when
        User findUser = userRepository.save(user); // 영속화 됨

        // then
        System.out.println(findUser);
    }

    @Test
    public void save_test() {
        // given
        User user = new User(); // 비영속 객체
        user.setUsername("love");
        user.setPassword("1234");
        user.setEmail("love@nate.com");

        // when
        User findUser = userRepository.save(user); // 영속화 됨

        // eye
        System.out.println(findUser);
    }

    @Test
    public void findByUsername_test() {
        // given
        String username = "ssar";

        // when (ssar, 1234)
        User findUser = userRepository.findByUsername(username);

        // 로그인 기능 중 패스워드 검증
        if (findUser.getPassword().equals("1234")) {
        }
        // eye
        System.out.println(findUser);
    }

}
