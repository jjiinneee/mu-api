package com.musinsa.muapi.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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
  @NotEmpty
  private String brand;
  @NotEmpty
  private String cateName;
  @NotEmpty
  private Integer price;
  
  public void brandNameChange(String brand, String cateName, Integer price) {
    this.brand = brand;
    this.cateName = cateName;
    this.price = price;
  }
}
