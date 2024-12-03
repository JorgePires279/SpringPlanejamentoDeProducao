package com.exemple.demo.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.exemple.demo.dtos.CreateOSDto;
import com.exemple.demo.dtos.OSDto;
import com.exemple.demo.dtos.PaginacaoDto;
import com.exemple.demo.services.OSService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/oss")
public class OSController {

    OSService osService;

    public OSController(OSService osService){
        this.osService = osService;
    }

    @GetMapping("/tipo/{tipo}")
    @ResponseStatus(HttpStatus.OK)
    public List<OSDto> getOSByTipo(@PathVariable String tipo) {
        return osService.getOSByTipo(tipo);
    }
    
    @GetMapping("/paginacao")
    public PaginacaoDto<OSDto> getOS(Pageable pageable) {
        Page<OSDto> page = osService.getOS(pageable);
        return new PaginacaoDto<>(page);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OSDto> getAllOS(){
        return osService.getAllOS();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OSDto getOS(@PathVariable Integer id){
        return osService.getOSById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OSDto createOS(@RequestBody @Valid CreateOSDto osDto){
        return osService.createOS(osDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OSDto updateOS(@PathVariable Integer id, @RequestBody OSDto osDto){
        return osService.updateOS(id, osDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OSDto deleteOS(@PathVariable Integer id){
        return osService.deleteOS(id);
    }
}
