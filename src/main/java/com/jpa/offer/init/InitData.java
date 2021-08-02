package com.jpa.offer.init;

import com.jpa.offer.entity.Role;
import com.jpa.offer.entity.User;
import lombok.RequiredArgsConstructor;
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
    }

    @Component
    static class InitDataService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void initUser() {
            for (int i = 1; i <= 25; i++) {
                if(i < 5){
                    em.persist(new User("관리자" + i, "admin" + i + "@email.com", Role.USER));
                }else{
                    em.persist(new User("일반 사용자" + i, "user" + i + "@email.com", Role.USER));
                }
            }
        }
    }
}
