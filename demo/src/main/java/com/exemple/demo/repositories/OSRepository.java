package com.exemple.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exemple.demo.models.OS;

public interface OSRepository extends JpaRepository<OS, Integer>{
}