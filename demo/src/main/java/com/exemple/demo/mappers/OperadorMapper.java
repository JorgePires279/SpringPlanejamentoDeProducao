package com.exemple.demo.mappers;

import org.springframework.stereotype.Component;

import com.exemple.demo.dtos.CreateOperadorDto;
import com.exemple.demo.dtos.OperadorDto;
import com.exemple.demo.models.Operador;

@Component
public class OperadorMapper {
  public OperadorDto toDto(Operador operador) {
    return new OperadorDto(
      operador.getId(),
      operador.getNome(),
      operador.getEmail(),
      operador.getSalario(),
      operador.getDataNascimento()
    );
  }

  public Operador toModel(CreateOperadorDto operadorDto) {
    return new Operador(
      operadorDto.nome(),             
      operadorDto.email(),            
      operadorDto.salario(),           
      operadorDto.dataNascimento()     
    );
  }
}