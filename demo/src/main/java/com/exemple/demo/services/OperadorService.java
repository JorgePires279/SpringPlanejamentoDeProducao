package com.exemple.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exemple.demo.dtos.CreateOperadorDto;
import com.exemple.demo.dtos.OperadorDto;
import com.exemple.demo.exceptions.ConflitoException;
import com.exemple.demo.exceptions.NaoEncontradoException;
import com.exemple.demo.exceptions.RequisicaoErradaException;
import com.exemple.demo.mappers.OperadorMapper;
import com.exemple.demo.models.Operador;
import com.exemple.demo.repositories.OperadorRepository;

@Service
public class OperadorService {

  private final OperadorRepository operadorRepository;
  private final OperadorMapper operadorMapper;
  
  @Value("${app.email.operadores}")
  private List<String> tiposPermitidos;

  public OperadorService(OperadorRepository operadorRepository, OperadorMapper operadorMapper){
    this.operadorRepository = operadorRepository;
    this.operadorMapper = operadorMapper;
  }

  public List<OperadorDto> getAllOperador() {
    return operadorRepository.findAll().stream().map(operadorMapper::toDto).toList();
  }
  
  public OperadorDto getOperadorById(Integer id) {
    return operadorMapper.toDto(operadorRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Operador não encontrado")));
  }

  public OperadorDto createOperador(CreateOperadorDto operadorDto) {
    if (!tiposPermitidos.contains(operadorDto.email())) {
      throw new RequisicaoErradaException("Email do operador inválido");
    }

    operadorRepository.findByNome(operadorDto.nome()).ifPresent(
      operador -> {
        throw new ConflitoException("Operador já cadastrado");
      }
    );

    Operador operador = operadorMapper.toModel(operadorDto);
    return operadorMapper.toDto(operadorRepository.save(operador));
  }

  public OperadorDto updateOperador(Integer id, OperadorDto operadorDto) {
    Operador operador = operadorRepository.findById(id).orElseThrow(
      () -> new NaoEncontradoException("Operador não encontrada")
    );
    if (operadorDto.nome() != null) operador.setNome(operadorDto.nome());
    if (operadorDto.email() != null) operador.setEmail(operadorDto.email());
    if (operadorDto.salario() != null) operador.setSalario(operadorDto.salario());
    if (operadorDto.dataNascimento() != null) operador.setDataNascimento(operadorDto.dataNascimento());
    if (!tiposPermitidos.contains(operador.getEmail())) {
      throw new RequisicaoErradaException("Email de operador inválido");
    }

    operadorRepository.findByNome(operador.getNome()).ifPresent(
      operadorCadastrado -> {
        if (!operadorCadastrado.getId().equals(operador.getId())) {
          throw new ConflitoException("Operador já cadastrado");
        }
      }
    );

    return operadorMapper.toDto(operadorRepository.save(operador));
  }

  public OperadorDto deleteOperador(Integer id) {
    Operador operador = operadorRepository.findById(id).orElseThrow(
      () -> new NaoEncontradoException("Operador não encontrado")
    );
    
    operadorRepository.delete(operador);
    return operadorMapper.toDto(operador);
  }
}