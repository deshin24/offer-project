package com.jpa.offer.service;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.entity.Answer;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.AnswerRepository;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    /**
     * 답변 등록
     * @param offerId
     * @param answerCreateRequestDto
     * @return
     */
    @Transactional
    public Long create(Long offerId, AnswerCreateRequestDto answerCreateRequestDto) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다."));

        if(offer.getAnswer() != null) throw new IllegalArgumentException("이미 답변이 완료된 제안글입니다.");

        User user = userRepository.findById(answerCreateRequestDto.getUserId())
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        Answer answer = answerRepository.save(answerCreateRequestDto.toEntity().changeUser(user));
        offer.changeAnswer(answer);

        return answer.getId();
    }
}
