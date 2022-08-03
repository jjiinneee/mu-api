package com.musinsa.muapi.controller;


import com.musinsa.muapi.dto.CatePriceDTO;
import com.musinsa.muapi.service.CodyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CodyController {

  private final CodyService codyService;
  
  public static class CodyNotFoundException extends RuntimeException{
  
  }
  
  //모든 카테고리의 상품을 브랜드별로 자유롭게 선택 가격 조회 API
  @GetMapping("/cate/free")
  public List cateFreeMin(){
    List fashionDTOs =  codyService.cateFreeMin();
    if(fashionDTOs == null){
      throw new CodyNotFoundException();
    }
    
    return fashionDTOs;
  }
  
  //한브랜드에서 모든 카테고리의 상품을 한꺼번에 구매
  @GetMapping("/cate/all")
  public CatePriceDTO getCateAllPurch(){
    CatePriceDTO catePriceDTO = codyService.getCateAllPurch();
    if(catePriceDTO == null){
      throw new CodyNotFoundException();
    }
    return catePriceDTO;
  }
  
//  각카테고리 이름으로 최소,최대 가격 조회 API
  @GetMapping("/cate/name")
  public Collection<CatePriceDTO> getCateName(String cateName){
    Collection<CatePriceDTO> cateList = codyService.getCateName(cateName);
    if(cateList == null){
      throw new CodyNotFoundException();
    }
    return cateList;
  }
}

