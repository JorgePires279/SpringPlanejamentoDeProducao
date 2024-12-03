package com.exemple.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.exemple.demo.models.OS;

public interface OSRepository extends JpaRepository<OS, Integer> {
    public List<OS> findByTipo(String tipo);

    @Query("SELECT p FROM OS p")
    Page<OS> findAllOS(Pageable pageable);
}