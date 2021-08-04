package com.jpa.offer.repository;

import com.jpa.offer.dto.FileResponseDto;
import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.jpa.offer.entity.QAnswer.answer;
import static com.jpa.offer.entity.QFile.file;
import static com.jpa.offer.entity.QOffer.offer;
import static com.jpa.offer.entity.QUser.user;

public class OfferRepositoryImpl implements OfferRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OfferRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 제안글 검색 리스트 (+페이징)
     * @param condition
     * @param pageable
     * @return
     */
    @Override
    public Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable) {

        String searchKeyword = condition.getSearchKeyword();
        Long offerId = null;
        if(searchKeyword.matches("[+-]?\\d*(\\.\\d+)?")){
            offerId = Long.parseLong(searchKeyword);
        }

       QueryResults<OfferListResponseDto> results = queryFactory
                .select(Projections.fields(
                        OfferListResponseDto.class,
                        offer.id,
                        offer.title,
                        offer.content,
                        offer.serviceType,
                        offer.companyName,
                        offer.managerName,
                        offer.phone,
                        offer.createdDate))
               .from(offer)
               .where(
                       titleLike(searchKeyword).or(
                               contentLike(searchKeyword).or(
                                       companyEq(searchKeyword).or(offerIdEq(offerId)))),
                       serviceTypeEq(condition.getServiceType()))
               .orderBy(
                       offer.createdDate.desc())
               .offset(pageable.getOffset())
               .limit(pageable.getPageSize())
               .fetchResults();

       List<OfferListResponseDto> content = results.getResults();
       long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 제안글 상세 (+회원정보, 답변정보)
     * @param id
     * @return
     */
    @Override
    public OfferDetailResponseDto detail(Long id) {

        OfferDetailResponseDto result = getOfferDetailResponseDto(id);
        List<FileResponseDto> files = getFileResponseDtos(id);
        if(files.size() > 0) result.setFiles(files);

        return result;
    }

    // 해당 제안글의 파일 리스트
    private List<FileResponseDto> getFileResponseDtos(Long id) {
        return queryFactory
                .select(Projections.fields(
                        FileResponseDto.class,
                        file.id,
                        file.orgTitle.as("title"),
                        file.path))
                .from(file)
                .where(fileOfferIdEq(id))
                .fetchResults().getResults();
    }

    private OfferDetailResponseDto getOfferDetailResponseDto(Long id) {
        return queryFactory
                .select(Projections.fields(
                        OfferDetailResponseDto.class,
                        offer.id.as("offerId"),
                        offer.offer.title,
                        offer.content,
                        offer.serviceType,
                        offer.companyName,
                        offer.managerName,
                        offer.phone,
                        offer.createdDate.as("offerCreatedDate"),
                        offer.modifiedDate.as("offerModifiedDate"),
                        user.id.as("userId"),
                        user.name.as("userName"),
                        user.email.as("userEmail"),
                        user.role,
                        answer.id.as("answerId"),
                        answer.content.as("answerContent"),
                        answer.createdDate.as("answerCreatedDate"),
                        answer.modifiedDate.as("answerModifiedDate")))
                .from(offer)
                .innerJoin(offer.user(), user)
                .leftJoin(offer.answer(), answer)
                .where(offerIdEq(id))
                .fetchOne();
    }

    private BooleanExpression fileOfferIdEq(Long id) {
        return id != null ? file.offer().id.eq(id) : null;
    }

    private BooleanExpression offerIdEq(Long id) {
        return id != null ? offer.id.eq(id) : null;
    }

    private BooleanExpression titleLike(String title) {
        return title != null ? offer.title.contains(title) : null;
    }

    private BooleanExpression contentLike(String content) {
        return content != null ? offer.content.contains(content) : null;
    }

    private BooleanExpression serviceTypeEq(OfferServiceType serviceType) {
        return serviceType != null ? offer.serviceType.eq(serviceType) : null;
    }

    private BooleanExpression companyEq(String companyName) {
        return companyName != null ? offer.companyName.eq(companyName) : null;
    }
}
