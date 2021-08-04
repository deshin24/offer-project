package com.jpa.offer.repository;

import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import com.jpa.offer.entity.Role;
import com.jpa.offer.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class OfferRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;

    /**
     * 제안 등 테스트
     */
    @Test
    public void createOfferTest() {
        // given
        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        // 제안 생성
        Offer offer = Offer.builder()
                .title("제안합니다")
                .content("스낵24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.SNACK24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();
        Long offerId = offerRepository.save(offer).getId();

        // when
        Offer findOffer = offerRepository.findById(offerId).get();

        // then
        Assertions.assertThat(findOffer.getTitle()).isEqualTo(offer.getTitle());
        Assertions.assertThat(findOffer.getUser().getName()).isEqualTo(offer.getUser().getName());
    }
}
