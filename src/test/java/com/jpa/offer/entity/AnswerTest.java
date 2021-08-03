package com.jpa.offer.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class AnswerTest {

    @Autowired
    EntityManager em;

    @Test
    public void answerTest(){
        // given

        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        em.persist(user);

        Answer answer = Answer.builder()
                .content("답변드립니다.")
                .user(user)
                .build();
        em.persist(answer);

        // 제안 생성
        Offer offer = Offer.builder()
                .title("제안합니다")
                .content("스낵24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.SNACK24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .answer(answer)
                .build();
        em.persist(offer);

        em.flush();
        em.clear();

        // when
        Answer result = em.createQuery("select a from Answer a", Answer.class).getSingleResult();

        // then
        Assertions.assertThat(result.getContent()).isEqualTo("답변드립니다.");
        // LAZY 확인
        Assertions.assertThat(result.getUser().getRole()).isEqualTo(Role.ADMIN);

    }
}
