package com.jpa.offer.service;

import com.jpa.offer.entity.Role;
import com.jpa.offer.repository.AnswerRepository;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserCheckService {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final AnswerRepository answerRepository;

    public Boolean checkUser(Long userId){
        Role role = userRepository.findById(userId)
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getRole();
        if(Role.USER.equals(role)) return true;
        return false;
    }

    public Boolean checkAdmin(Long userId){
        Role role = userRepository.findById(userId)
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getRole();
        if(Role.ADMIN.equals(role)) return true;
        return false;
    }

    public Boolean hasAuth(Long userId, Long offerId, Long answerId){
        Role role = userRepository.findById(userId)
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getRole();
        if(offerId == null){// 답변 권한 있는지( ROLE_ADMIN ), 해당 글 등록자인지 체크
            Long findUserId = answerRepository.findById(answerId)
                    .orElseThrow(()->new EntityNotFoundException("존재하지 않는 답변입니다")).getUser().getId();
            if(Role.ADMIN.equals(role) && userId.equals(findUserId)) return true;

        }else if(answerId == null){// 제안 권한 있는지 ( ROLE_USER ), 해당 글 등록자인지 체크
            Long findUserId = offerRepository.findById(offerId)
                    .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글입니다.")).getUser().getId();
            if(Role.USER.equals(role) && userId.equals(findUserId)) return true;
        }
        return false;
    }

    public Boolean hasReadAuth(Long userId, Long offerId){
        Role role = userRepository.findById(userId)
                .orElseThrow( () ->new EntityNotFoundException("존재하지 않는 사용자 입니다.")).getRole();
        Long findUserId = offerRepository.findById(offerId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 제안글입니다.")).getUser().getId();
        if(Role.ADMIN.equals(role) || userId.equals(findUserId)) return true;
        return false;
    }

}
