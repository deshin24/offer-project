package com.jpa.offer.repository;

import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.OfferUpdateRequestDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.entity.Offer;
import com.jpa.offer.entity.OfferServiceType;
import com.jpa.offer.entity.Role;
import com.jpa.offer.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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
     * 제안 등록 테스트
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

    /**
     * 제안 리스트 테스트
     */
    @Test
    public void offerListTest(){
        // given
        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        // 제안 생성
        Offer offer1 = Offer.builder()
                .title("제안합니다1")
                .content("스낵24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.SNACK24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();

        Offer offer2 = Offer.builder()
                .title("제안합니다2")
                .content("생일24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.BIRTH24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();
        offerRepository.save(offer1);
        offerRepository.save(offer2);

        // when
        List<Offer> offers = offerRepository.findAll();

        // then
        Assertions.assertThat(offers.get(0).getServiceType()).isEqualTo(OfferServiceType.SNACK24);
    }

    /**
     * 제안 상세 테스트
     */
    @Test
    public void offerDetailTest(){
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
        OfferDetailResponseDto detailResponseDto = offerRepository.detail(offerId);

        // then
        Assertions.assertThat(detailResponseDto.getContent()).isEqualTo(offer.getContent());
        Assertions.assertThat(detailResponseDto.getUserName()).isEqualTo(user.getName());
    }

    /**
     * 제안 수정 테스트
     */
    @Test
    public void offerUpdateTest(){
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
        OfferUpdateRequestDto updateRequestDto = new OfferUpdateRequestDto();
        updateRequestDto.setManagerName("박직원");
        offer.update(updateRequestDto);

        // then
        Offer findOffer = offerRepository.findById(offerId).get();
        Assertions.assertThat(findOffer.getManagerName()).isEqualTo("박직원");
    }

    /**
     * 제안 삭제 테스트
     */
    @Test
    public void offerDeleteTest(){
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
        offerRepository.delete(offer);

        // then
        Assertions.assertThat(offerRepository.findById(offerId)).isEmpty();
    }

    /**
     * 제안 검색 리스트 테스트
     */
    @Test
    public void offerSearchTest(){
        // given
        // 회원 생성
        User user = User.builder()
                .name("회원1")
                .email("user1@email.com")
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        // 제안 생성
        Offer offer1 = Offer.builder()
                .title("제안합니다1")
                .content("스낵24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.SNACK24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();

        Offer offer2 = Offer.builder()
                .title("제안합니다2")
                .content("생일24에 자사 제품 판매를 제안합니다.")
                .serviceType(OfferServiceType.BIRTH24)
                .companyName("A회사")
                .managerName("김직원")
                .phone("010-0000-0000")
                .user(user)
                .build();
        offerRepository.save(offer1);
        offerRepository.save(offer2);

        // when
        SearchCondition searchCondition = new SearchCondition();
        //searchCondition.setCompany("A회사");
        searchCondition.setServiceType(OfferServiceType.BIRTH24);

        Pageable pageable = PageRequest.of(0, 10);

        Page<OfferListResponseDto> listResponseDtos = offerRepository.search(searchCondition, pageable);

        // then
        //Assertions.assertThat(listResponseDtos.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(listResponseDtos.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(listResponseDtos.getTotalPages()).isEqualTo(1);
    }
}
