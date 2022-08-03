package com.musinsa.muapi.controller;


import com.musinsa.muapi.dto.CatePriceDTO;
import com.musinsa.muapi.dto.FashionDTO;
import com.musinsa.muapi.service.FashionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FashionController {

  private final FashionService fashionService;
  
  public static class CodyNotFoundException extends RuntimeException{
  
  }
  
  //모든 카테고리의 상품을 브랜드별로 자유롭게 선택 가격 조회 API
  @GetMapping("/cate/free")
  public List cateFreeMin(){
    List fashionDTOs =  fashionService.cateFreeMin();
    if(fashionDTOs == null){
      throw new CodyNotFoundException();
    }
    
    return fashionDTOs;
  }
  
  //한브랜드에서 모든 카테고리의 상품을 한꺼번에 구매
  @GetMapping("/cate/all")
  public CatePriceDTO getCateAllPurch(){
    CatePriceDTO catePriceDTO = fashionService.getCateAllPurch();
    if(catePriceDTO == null){
      throw new CodyNotFoundException();
    }
    return catePriceDTO;
  }
  
//  각카테고리 이름으로 최소,최대 가격 조회 API
  @GetMapping("/cate/name")
  public Collection<CatePriceDTO> getCateName(String cateName){
    Collection<CatePriceDTO> cateList = fashionService.getCateName(cateName);
    if(cateList == null){
      throw new CodyNotFoundException();
    }
    return cateList;
  }
  
  //브랜드 상품 가격 추가
  @PostMapping("/brand/cateName")
  public void createBrandAddCate(
          @Valid FashionDTO fashionDTO, BindingResult bindingResult){
  
    if(bindingResult.hasErrors()) {
      log.info("errors.......");
    }
    fashionService.createBrandCate(fashionDTO);
  }
  
  
  //수정
  @PostMapping("/brand/modifyCateName")
  public void updateBrandCate(
          @Valid FashionDTO fashionDTO,
          BindingResult bindingResult){
    
    log.info("modify-------");
    
    if(bindingResult.hasErrors()) {
      log.info("update error.......");
    }
    fashionService.updateBrandCate(fashionDTO);
    
  }
  
  //삭제
  @PostMapping("/brand/deleteCateName")
  public void deleteBrandCate(Long id) {
    log.info("delete post.. " + id);
    fashionService.deleteBrandCate(id);
  
  }
}

