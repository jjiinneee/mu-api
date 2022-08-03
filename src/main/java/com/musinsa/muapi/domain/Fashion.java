package com.musinsa.muapi.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(indexes = @Index(name="idx_fashion_brand_cate_name", columnList = "brand, cateName"))
public class Fashion {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String brand;
  private String cateName;
  private Integer price;
  
  public void brandNameChange(String brand, String cateName, Integer price) {
    this.brand = brand;
    this.cateName = cateName;
    this.price = price;
  }
}
