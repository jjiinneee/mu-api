package com.musinsa.muapi.repository;

import com.musinsa.muapi.domain.Cate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateRepository extends JpaRepository<Cate, String> {
  Cate findCateByCateName(String cateNaem);
}
