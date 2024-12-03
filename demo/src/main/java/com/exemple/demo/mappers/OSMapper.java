package com.exemple.demo.mappers;

import org.springframework.stereotype.Component;
import com.exemple.demo.dtos.CreateOSDto;
import com.exemple.demo.dtos.OSDto;
import com.exemple.demo.models.OS;

@Component
public class OSMapper {

  public OSDto toDto(OS os) {
    return new OSDto(
      os.getId(),
      os.getTipo(),
      os.getOp(),
      os.getQuantidadeOp(),
      os.getQuantidadeOS(),
      os.getIdProduto(),
      os.getNomeProduto(),
      os.getIdOperador(),
      os.getNomeOperador(),
      os.getTempoDeExecucao(),
      os.getProdutividade()
    );
  }

  public OS toModel(CreateOSDto osDto) {
    return new OS(
      osDto.tipo(),
      osDto.op(),
      osDto.quantidadeProduzida(),
      osDto.idProduto(),
      osDto.idOperador(),
      osDto.tempoDeExecucaoEmSegundos()
    );
  }
}
 