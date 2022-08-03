package com.musinsa.muapi.repository;

import com.musinsa.muapi.domain.Fashion;
import com.musinsa.muapi.domain.QFashion;
import com.musinsa.muapi.dto.CatePriceDTO;
import com.musinsa.muapi.dto.FashionDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Log4j2
public class CodyRepositoryTest {
  
  @PersistenceContext
  private EntityManager em;
 
  @Autowired
  private FashionRepository fashionRepository;
  
  @Test
  public void cateNameMin(){
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
  
   log.info(voList);
  
  }
  

  @Test
  public void getCateAll(){
    
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
    
    
    log.info(ent);
  }
  

  @Test
  public void getCateMaxMin(){
    String cateName = "top";
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
    
    List<CatePriceDTO> resultList =
            Stream.concat(resultMinQuery.stream(), resultManQuery.stream())
            .collect(Collectors.toList());

    log.info(resultMinQuery);
    log.info(resultManQuery);
    log.info(resultList);
  }
  
  
  @Test
  public void createBrandAddCate(){
    // 브랜드  상품 가격 추가
    Fashion fashion = Fashion.builder()
            .brand("E")
            .cateName("top")
            .price(50)
            .build();
  
  
    fashionRepository.save(fashion);
  }
  
  @Test
  public void updateBrandCate(){
    Long id = 134L;
    
    Optional<Fashion> result = fashionRepository.findById(id);
    Fashion fashion = result.orElseThrow();
  
    fashion.brandNameChange("A","pants", 1000);
    fashionRepository.save(fashion);
    log.info(fashion);
    
  }
  
  @Test
  public void deleteBrandCate(){
    Long id = 134L;
    
    fashionRepository.deleteById(id);
  }
}
