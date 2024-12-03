package com.exemple.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemple.demo.models.OP;

public interface OPRepository extends JpaRepository<OP, Integer>{
      public List<OP> findByTipo(String tipo);

         @Query("SELECT p FROM OP p")
    Page<OP> findAllOP(Pageable pageable);
}