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
class OfferTest {

    @Autowired EntityManager em;

    @Test
    public void offerTest(){
        // given

        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.USER)
                .build();
        em.persist(user);

        // 제안 생성
        Offer offer = Offer.builder()
                .title("제안합니다")
                .content("스낵24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.SNACK24)
                .company("A회사")
                .manager("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();
        em.persist(offer);

        em.flush();
        em.clear();

        // when
        List<Offer> offers = em.createQuery("select o from Offer o", Offer.class).getResultList();

        // then
        Assertions.assertThat(offers.get(0).getCompany()).isEqualTo("A회사");
        Assertions.assertThat(offers.get(0).getUser().getName()).isEqualTo("회원1");
    }

}
