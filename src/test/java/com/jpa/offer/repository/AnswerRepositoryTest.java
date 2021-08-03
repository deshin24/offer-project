package com.jpa.offer.repository;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.entity.*;
import com.jpa.offer.service.AnswerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class AnswerRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    AnswerService service;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    OfferRepository offerRepository;

    @Test
    public void answerCreateTest(){

        // given

        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        em.persist(user);

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
        em.persist(offer);

        String content = "답변 내용";

        AnswerCreateRequestDto dto = new AnswerCreateRequestDto();
        dto.setContent(content);
        dto.setUserId(1L);

        // answer 등록, offer에 저장 테스트
        service.create(1L, dto);

        em.flush();
        em.clear();

        // when
        OfferDetailResponseDto detailResponseDto = offerRepository.detail(1L);

        // then
        Assertions.assertThat(detailResponseDto.getAnswerContent()).isEqualTo(content);

    }
}
