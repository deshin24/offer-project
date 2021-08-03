package com.jpa.offer.repository;

import com.jpa.offer.dto.OfferListResponseDto;
import com.jpa.offer.dto.SearchCondition;
import com.jpa.offer.entity.OfferServiceType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.jpa.offer.entity.QOffer.offer;

public class OfferRepositoryImpl implements OfferRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OfferRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OfferListResponseDto> search(SearchCondition condition, Pageable pageable) {

       QueryResults<OfferListResponseDto> results = queryFactory
                .select(Projections.fields(
                        OfferListResponseDto.class,
                        offer.id,
                        offer.title,
                        offer.content,
                        offer.serviceType,
                        offer.company,
                        offer.manager,
                        offer.phone,
                        offer.createdDate))
               .from(offer)
               .where(
                       titleLike(condition.getTitle()),
                       contentLike(condition.getContent()),
                       serviceTypeEq(condition.getServiceType()),
                       companyEq(condition.getCompany()))
               .orderBy(
                       offer.createdDate.desc())
               .offset(pageable.getOffset())
               .limit(pageable.getPageSize())
               .fetchResults();

       List<OfferListResponseDto> content = results.getResults();
       long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
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

    private BooleanExpression companyEq(String company) {
        return company != null ? offer.company.eq(company) : null;
    }
}
