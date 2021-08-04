package com.jpa.offer.init;

import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import com.jpa.offer.entity.Role;
import com.jpa.offer.entity.User;
import com.jpa.offer.repository.OfferRepository;
import com.jpa.offer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitDataService initDataService;

    @PostConstruct
    public void init(){
        initDataService.initUser();
        initDataService.initOffer();
    }

    @Component
    static class InitDataService {
        @PersistenceContext
        private EntityManager em;
        @Autowired
        UserRepository userRepository;
        @Autowired
        OfferRepository offerRepository;

        @Transactional
        public void initUser() {
            for (int i = 1; i <= 25; i++) {
                if(i <= 5){
                    em.persist(new User("관리자" + i, "admin" + i + "@email.com", Role.ADMIN));
                }else{
                    em.persist(new User("일반 사용자" + i, "user" + i + "@email.com", Role.USER));
                }
            }
        }

        @Transactional
        public void initOffer(){
            for(int i = 1; i<=100; i++){
                if(i <= 25){
                    User user = userRepository.findById(6L).get();
                    Offer offer = Offer.builder()
                            .title("제안합니다" + i)
                            .content("스낵24에 자사 제품 판매를 제안합니다.")
                            .serviceType(OfferServiceType.SNACK24)
                            .companyName(i + "회사")
                            .managerName("직원"+i)
                            .phone("010-0000-0000")
                            .user(user)
                            .build();
                    offerRepository.save(offer);
                }else if(i >25 &&i <= 50){
                    User user = userRepository.findById(7L).get();
                    Offer offer = Offer.builder()
                            .title("제안합니다" + i)
                            .content("오피스24와의 000를 제안합니다.")
                            .serviceType(OfferServiceType.OFFICE24)
                            .companyName(i + "회사")
                            .managerName("직원"+i)
                            .phone("010-0000-0000")
                            .user(user)
                            .build();
                    offerRepository.save(offer);
                }else if(i >50 &&i <= 75){
                    User user = userRepository.findById(8L).get();
                    Offer offer = Offer.builder()
                            .title("제안합니다" + i)
                            .content("생일24와의 000를 제안합니다.")
                            .serviceType(OfferServiceType.BIRTH24)
                            .companyName(i + "회사")
                            .managerName("직원"+i)
                            .phone("010-0000-0000")
                            .user(user)
                            .build();
                    offerRepository.save(offer);
                }else{
                    User user = userRepository.findById(9L).get();
                    Offer offer = Offer.builder()
                            .title("제안합니다" + i)
                            .content("청소24와의 000를 제안합니다.")
                            .serviceType(OfferServiceType.CLEAN24)
                            .companyName(i + "회사")
                            .managerName("직원"+i)
                            .phone("010-0000-0000")
                            .user(user)
                            .build();
                    offerRepository.save(offer);
                }
            }
        }
    }
}
