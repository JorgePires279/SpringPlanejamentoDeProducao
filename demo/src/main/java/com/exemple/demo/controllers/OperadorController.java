package com.exemple.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.demo.dtos.CreateOperadorDto;
import com.exemple.demo.dtos.OperadorDto;
import com.exemple.demo.services.OperadorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/operadores")
public class OperadorController {

    private final OperadorService operadorService;

    @Autowired
    public OperadorController(OperadorService operadorService){
        this.operadorService = operadorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OperadorDto> getAllOperador(){
        return operadorService.getAllOperador();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OperadorDto getOperador(@PathVariable Integer id){
        return operadorService.getOperadorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OperadorDto createOperador(@RequestBody @Valid CreateOperadorDto operadorDto){
        return operadorService.createOperador(operadorDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OperadorDto updateOperador(@PathVariable Integer id, @RequestBody OperadorDto operadorDto){
        return operadorService.updateOperador(id, operadorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OperadorDto deleteOperador(@PathVariable Integer id){
        return operadorService.deleteOperador(id);
    }
}
