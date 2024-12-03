package com.exemple.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.exemple.demo.dtos.CreateProdutoDto;
import com.exemple.demo.dtos.ProdutoDto;
import com.exemple.demo.dtos.PaginacaoDto;
import com.exemple.demo.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/tipo/{tipo}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDto> getProdutosByTipo(@PathVariable String tipo) {
        return produtoService.getProdutosByTipo(tipo);
    }
    /* APARECE COM LIXO APÓS A LISTA PAGINADA
        @GetMapping("/paginacao")
        public Page<ProdutoDto> getProdutos(Pageable pageable) {
            return produtoService.getProdutos(pageable);
        }
    */
    //http://localhost:8080/produtos/paginacao?page=0&size=2&sort=nome,asc
    //http://localhost:8080/produtos/paginacao?page=0&size=2
    @GetMapping("/paginacao")
    public PaginacaoDto<ProdutoDto> getProdutos(Pageable pageable) {
        Page<ProdutoDto> page = produtoService.getProdutos(pageable);
        return new PaginacaoDto<>(page);
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


