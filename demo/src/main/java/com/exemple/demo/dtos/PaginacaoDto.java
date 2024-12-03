package com.exemple.demo.dtos;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaginacaoDto<T> {
    private List<T> conteudoPorPagina;
    private int totalDePages;
    private long totalElementos;
    private int currentPage;

    public PaginacaoDto(Page<T> page) {
        this.conteudoPorPagina = page.getContent();
        this.totalDePages = page.getTotalPages();
        this.totalElementos = page.getTotalElements();
        this.currentPage = page.getNumber();
    }


    public List<T> getconteudoPorPagina() {
        return conteudoPorPagina;
    }

    public void setprodutosPorPagina(List<T> produtosPorPagina) {
        this.conteudoPorPagina = produtosPorPagina;
    }

    public int getTotalDePaginas() {
        return totalDePages;
    }

    public void setTotalDePagina(int totalDePages) {
        this.totalDePages = totalDePages;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public String getPaginasAtual() {
        return IntStream.range(0, totalDePages).mapToObj(i -> (i == currentPage ? "[" + (i) + "]" : String.valueOf(i))).collect(Collectors.joining(", "));
    }

}