package com.exemple.demo.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemple.demo.models.Operador;

public interface OperadorRepository extends JpaRepository<Operador, Integer> {
  Optional<Operador> findByNome(String nome);
}
