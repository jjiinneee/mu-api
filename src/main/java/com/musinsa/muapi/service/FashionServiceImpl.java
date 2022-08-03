package com.musinsa.muapi.service;


import com.musinsa.muapi.domain.Fashion;
import com.musinsa.muapi.domain.QFashion;
import com.musinsa.muapi.dto.CatePriceDTO;
import com.musinsa.muapi.dto.FashionDTO;
import com.musinsa.muapi.repository.FashionRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class FashionServiceImpl implements FashionService {
  
  @PersistenceContext
  private EntityManager em;
  
  private final FashionRepository fashionRepository;
  
  @Override
  public List<FashionDTO> cateFreeMin(){
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
  
    QFashion qFashion = QFashion.fashion;
    QFashion qSubFashion = new QFashion("qSubFashion");
    JPAQuery<FashionDTO> jpaQuery
            = queryFactory.select(Projections.fields(
                    FashionDTO.class,
                    qFashion.brand.max().as("brand"),
                    qFashion.cateName.as("cateName"),
                    qFashion.price.as("price")
            ))
            .from(qFashion)
            .where(qFashion.price.eq(JPAExpressions.select(qSubFashion.price.min()).from(qSubFashion).where(qSubFashion.cateName.eq(qFashion.cateName)))
            ).groupBy(qFashion.cateName, qFashion.price);
  
    List<FashionDTO> resultQuery = jpaQuery.fetch();
  
    List<FashionDTO> voList = new ArrayList<>();
  
    Integer totalPrice = 0;
  
    for (int i = 0; i < resultQuery.size(); i++) {
      FashionDTO ent = resultQuery.get(i);
      totalPrice += ent.getPrice();
      ent.setTotalPrice(totalPrice);
      voList.add(ent);
    }
    
    return voList;
  }
  
  
  @Override
  public CatePriceDTO getCateAllPurch(){
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    QFashion qFashion = QFashion.fashion;
  
    JPAQuery<FashionDTO> jpaQuery
            = queryFactory.select(Projections.fields(
                    FashionDTO.class,
                    qFashion.brand.as("brand"),
                    qFashion.price.sum().as("price")
          
            ))
            .from(qFashion)
            .groupBy(qFashion.brand)
            .orderBy(qFashion.price.sum().asc());
  
    List<FashionDTO> resultQuery = jpaQuery.fetch();
    
    CatePriceDTO ent = new CatePriceDTO();
    ent.setPrice(resultQuery.get(0).getPrice());
    ent.setBrand(resultQuery.get(0).getBrand());
  
    return ent;
  }
  
  @Override
  public Collection<CatePriceDTO> getCateName(String cateName){
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
  
    QFashion qFashion = QFashion.fashion;
  
    JPAQuery<CatePriceDTO> jpaMinQuery
            = queryFactory.select(Projections.fields(
                    CatePriceDTO.class,
                    qFashion.brand.as("brand"),
                    qFashion.price.as("price")
            ))
            .from(qFashion)
            .where(qFashion.cateName.eq(cateName))
            .orderBy(qFashion.price.asc()).limit(1);
  
    JPAQuery<CatePriceDTO> jpaMaxQuery
            = queryFactory.select(Projections.fields(
                    CatePriceDTO.class,
                    qFashion.brand.as("brand"),
                    qFashion.price.as("price")
            ))
            .from(qFashion)
            .where(qFashion.cateName.eq(cateName))
            .orderBy(qFashion.price.desc()).limit(1);
  
    Collection<CatePriceDTO> resultMinQuery = jpaMinQuery.fetch();
    Collection<CatePriceDTO> resultManQuery = jpaMaxQuery.fetch();
  
  
    List<CatePriceDTO> resultList =   Stream.concat(resultMinQuery.stream(), resultManQuery.stream())
            .collect(Collectors.toList());
    
    return resultList;
  }
  
  
  @Override
  public void createBrandCate(FashionDTO fashionDTO){
    
    Fashion fashion = Fashion.builder()
            .brand(fashionDTO.getBrand())
            .cateName(fashionDTO.getCateName())
            .price(fashionDTO.getPrice())
            .build();
    
    fashionRepository.save(fashion);
  }
  
  @Override
  public void updateBrandCate(FashionDTO fashionDTO){
  
    Optional<Fashion> result = fashionRepository.findById(fashionDTO.getId());
  
    Fashion fashion = result.orElseThrow();
  
    fashion.brandNameChange(fashion.getBrand(), fashion.getCateName(), fashion.getPrice());
  
    fashionRepository.save(fashion);
  }
  
  
  @Override
  public void deleteBrandCate(Long id){
    fashionRepository.deleteById(id);
  }
}
