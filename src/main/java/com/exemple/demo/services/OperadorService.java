package com.exemple.demo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemple.demo.dtos.CreateOperadorDto;
import com.exemple.demo.dtos.OperadorDto;
import com.exemple.demo.exceptions.ErroConflitoException;
import com.exemple.demo.exceptions.ErroNaoEncontradoException;
import com.exemple.demo.exceptions.ErroRequisicaoErradaException;
import com.exemple.demo.mappers.OperadorMapper;
import com.exemple.demo.models.Operador;
import com.exemple.demo.repositories.OperadorRepository;
import com.exemple.demo.repositories.OSRepository;

@Service
public class OperadorService {

  private final OperadorRepository operadorRepository;
  private final OSRepository osRepository;
  private final OperadorMapper operadorMapper;

  public OperadorService(OperadorRepository operadorRepository, OperadorMapper operadorMapper,OSRepository osRepository){
    this.operadorRepository = operadorRepository;
    this.osRepository = osRepository;
    this.operadorMapper = operadorMapper;
  }

  public Page<OperadorDto> getOperador(Pageable pageable) {
    Page<Operador> operador = operadorRepository.findAllOperador(pageable);

    if (operador.isEmpty()) {
      throw new ErroRequisicaoErradaException("Não há Operador disponíveis na página solicitada.");
    }

    return operador.map(operadorMapper::toDto);
  }

  public List<OperadorDto> getAllOperador() {
    return operadorRepository.findAll().stream().map(operadorMapper::toDto).toList();
  }
  
  public OperadorDto getOperadorById(Integer id) {
    return operadorMapper.toDto(operadorRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("Operador não encontrado")));
  }

  public OperadorDto createOperador(CreateOperadorDto operadorDto) {

    operadorRepository.findByNome(operadorDto.nome()).ifPresent(
      operador -> {
        throw new ErroConflitoException("Operador já cadastrado");
      }
    );

    Operador operador = operadorMapper.toModel(operadorDto);
    return operadorMapper.toDto(operadorRepository.save(operador));
  }

  public OperadorDto updateOperador(Integer id, OperadorDto operadorDto) {
    Operador operador = operadorRepository.findById(id).orElseThrow(
      () -> new ErroNaoEncontradoException("Operador não encontrada")
    );
    if (operadorDto.nome() != null) operador.setNome(operadorDto.nome());
    if (operadorDto.email() != null) operador.setEmail(operadorDto.email());
    if (operadorDto.salario() != null) operador.setSalario(operadorDto.salario());
    if (operadorDto.dataNascimento() != null) operador.setDataNascimento(operadorDto.dataNascimento());
    
    operadorRepository.findByNome(operador.getNome()).ifPresent(
      operadorCadastrado -> {
        if (!operadorCadastrado.getId().equals(operador.getId())) {
          throw new ErroConflitoException("Operador já cadastrado");
        }
      }
    );

    return operadorMapper.toDto(operadorRepository.save(operador));
  }
 
  public OperadorDto deleteOperador(Integer id) {

    Operador operador = operadorRepository.findById(id).orElseThrow(() -> new ErroNaoEncontradoException("Operador não encontrado"));

    var operadorEmUso = osRepository.findById(id).orElse(null);
    if (operadorEmUso != null) {
        throw new ErroConflitoException("Operador não pode ser deletado, pois está em uso.");
    }
    
    operadorRepository.delete(operador);
    return operadorMapper.toDto(operador);
  }

}