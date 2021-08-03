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
class FileTest {

    @Autowired EntityManager em;

    @Test
    public void fileTest(){
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
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();
        em.persist(offer);

        // 파일 생성
        File file1 = File.builder()
                .orgTitle("이미지1.jpg")
                .path("이미지1 경로 ~ ")
                .offer(offer)
                .build();

        File file2 = File.builder()
                .orgTitle("이미지2.jpg")
                .path("이미지2 경로 ~ ")
                .offer(offer)
                .build();
        em.persist(file1);
        em.persist(file2);

        em.flush();
        em.clear();

        // when
        List<File> files = em.createQuery("select f from File f", File.class).getResultList();

        // then
        Assertions.assertThat(files.size()).isEqualTo(2);
        // FetchType.LAZY 확인
        Assertions.assertThat(files.get(0).getOffer().getTitle()).isEqualTo("제안합니다");
    }
}
