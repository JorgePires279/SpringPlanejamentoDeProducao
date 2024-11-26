package com.exemple.demo.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.exemple.demo.dtos.CreateOPDto;
import com.exemple.demo.dtos.OPDto;
import com.exemple.demo.services.OPService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ops")
public class OPController {

    private final OPService opService;

    public OPController(OPService opService) {
        this.opService = opService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OPDto> getAllOP() {
        return opService.getAllOP();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OPDto getOPById(@PathVariable Integer id) {
        return opService.getOPById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OPDto createOP(@RequestBody @Valid CreateOPDto opDto) {
        return opService.createOP(opDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OPDto updateOP(@PathVariable Integer id, @RequestBody OPDto opDto) {
        return opService.updateOP(id, opDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OPDto deleteOP(@PathVariable Integer id) {
        return opService.deleteOP(id);
    }
}
