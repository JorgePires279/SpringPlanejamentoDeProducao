package com.exemple.demo.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemple.demo.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  public Optional<Produto> findByNome(String nome);
}
