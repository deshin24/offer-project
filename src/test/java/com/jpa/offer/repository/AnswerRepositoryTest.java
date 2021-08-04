package com.jpa.offer.repository;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.dto.AnswerUpdateRequestDto;
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
    UserRepository userRepository;
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
        Long userId = userRepository.save(user).getId();

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

        // 답변생성
        String content = "답변 내용";

        AnswerCreateRequestDto dto = new AnswerCreateRequestDto();
        dto.setContent(content);
        dto.setUserId(userId);

        // answer 등록, offer에 저장 테스트
        service.create(offerId, dto);

        // when
        OfferDetailResponseDto detailResponseDto = offerRepository.detail(offerId);

        // then
        Assertions.assertThat(detailResponseDto.getAnswerContent()).isEqualTo(content);
    }

    @Test
    public void answerUpdateTest(){
        // given
        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        Long userId = userRepository.save(user).getId();

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

        // 답변 생성
        Answer answer = Answer.builder()
                .content("답변 내용")
                .user(user)
                .build();
        answerRepository.save(answer);
        offer.changeAnswer(answer);

        // when
        // 답변 수정
        AnswerUpdateRequestDto updateRequestDto = new AnswerUpdateRequestDto();
        updateRequestDto.setContent("답변 수정");
        answer.update(updateRequestDto);

        OfferDetailResponseDto detailResponseDto = offerRepository.detail(offerId);

        // then
        Assertions.assertThat(detailResponseDto.getAnswerContent()).isEqualTo("답변 수정");
    }
}
