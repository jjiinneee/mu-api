package com.musinsa.muapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FashionDTO  {
  
  private Long id;
  private String brand;
  private String cateName;
  private Integer price;
  private Integer totalPrice;
}
