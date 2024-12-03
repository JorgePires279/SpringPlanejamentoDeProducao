package com.exemple.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exemple.demo.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  public Optional<Produto> findByNome(String nome);
  public List<Produto> findByTipo(String tipo);

   @Query("SELECT p FROM Produto p")
    Page<Produto> findAllProduto(Pageable pageable);
}
