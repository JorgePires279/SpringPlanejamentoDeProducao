package com.exemple.demo.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemple.demo.models.Operador;

public interface OperadorRepository extends JpaRepository<Operador, Integer> {
  Optional<Operador> findByNome(String nome);

     @Query("SELECT p FROM Operador p")
    Page<Operador> findAllOperador(Pageable pageable);
}
