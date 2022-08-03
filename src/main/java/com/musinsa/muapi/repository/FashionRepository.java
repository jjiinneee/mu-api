package com.musinsa.muapi.repository;

import com.musinsa.muapi.domain.Fashion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FashionRepository extends JpaRepository<Fashion, Long> {


}
