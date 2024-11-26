package com.exemple.demo.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.exemple.demo.dtos.CreateProdutoDto;
import com.exemple.demo.dtos.ProdutoDto;
import com.exemple.demo.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDto> getAllProdutos() {
        return produtoService.getAllProdutos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDto getProduto(@PathVariable Integer id) {
        return produtoService.getProdutoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto createProduto(@RequestBody @Valid CreateProdutoDto produtoDto) {
        return produtoService.createProduto(produtoDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDto updateProduto(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto) {
        return produtoService.updateProduto(id, produtoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDto deleteProduto(@PathVariable Integer id) {
        return produtoService.deleteProduto(id);
    }
}


