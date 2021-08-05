package com.jpa.offer.repository;

import com.jpa.offer.dto.FileResponseDto;
import com.jpa.offer.dto.OfferDetailResponseDto;
import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.SearchCondition;
import com.querydsl.core.BooleanBuilder;
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
               .where(getBooleanBuilder(condition))
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

    // or 조건을 위해 BooleanBuilder 사용
    private BooleanBuilder getBooleanBuilder(SearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();
        String keyword = condition.getSearchKeyword();

        if (keyword != null) {
            // 숫자일 경우 offer id로 검색
            if(keyword.matches("[+-]?\\d*(\\.\\d+)?")){
                Long offerId = Long.parseLong(keyword);
                builder.and(offer.id.eq(offerId));
            }else{
                builder.or(offer.title.contains(keyword));
                builder.or(offer.content.contains(keyword));
                builder.or(offer.companyName.eq(keyword));
            }
        }
        return builder;
    }

    private BooleanExpression fileOfferIdEq(Long id) {
        return id != null ? file.offer().id.eq(id) : null;
    }

    private BooleanExpression offerIdEq(Long id) {
        return id != null ? offer.id.eq(id) : null;
    }

}
