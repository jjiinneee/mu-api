package com.musinsa.muapi.service;

import com.musinsa.muapi.dto.CatePriceDTO;
import com.musinsa.muapi.dto.FashionDTO;

import java.util.Collection;
import java.util.List;

public interface CodyService {
  List<FashionDTO> cateFreeMin();
  
  CatePriceDTO getCateAllPurch();
  
  Collection<CatePriceDTO> getCateName(String cateName);
}
