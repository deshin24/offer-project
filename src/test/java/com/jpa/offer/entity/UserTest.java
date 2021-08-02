package com.jpa.offer.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    EntityManager em;

    @Test
    public void userTest(){
        // given
        User user1 = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .name("회원2")
                .email("user2@email.com")
                .role(Role.USER)
                .build();

        em.persist(user1);
        em.persist(user2);

        em.flush();
        em.clear();

        // when
        List<User> userList = em.createQuery("select u from User u", User.class).getResultList();

        // then
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }
}
