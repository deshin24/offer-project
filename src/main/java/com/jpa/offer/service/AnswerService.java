package com.jpa.offer.service;

import com.jpa.offer.dto.AnswerCreateRequestDto;
import com.jpa.offer.dto.AnswerUpdateRequestDto;
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

    /**
     * 답변 수정
     * @param answerId
     * @param answerUpdateRequestDto
     * @return
     */
    @Transactional
    public Long update(Long answerId, AnswerUpdateRequestDto answerUpdateRequestDto) {
        Answer answer= answerRepository.findById(answerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 답변입니다."));

        answer.update(answerUpdateRequestDto);

        return answer.getId();
    }

    /**
     * 답변 삭제
     * @param offerId
     * @param answerId
     * @return
     */
    @Transactional
    public Long delete(Long offerId, Long answerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글 입니다."));
        offer.changeAnswer(null);

        Long findAnswerId = answerRepository.findById(answerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 답변입니다.")).getId();

        answerRepository.deleteById(answerId);

        return findAnswerId;
    }
}
