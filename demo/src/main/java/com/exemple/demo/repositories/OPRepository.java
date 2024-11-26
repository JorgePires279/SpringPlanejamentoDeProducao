package com.exemple.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exemple.demo.models.OP;

public interface OPRepository extends JpaRepository<OP, Integer>{
}