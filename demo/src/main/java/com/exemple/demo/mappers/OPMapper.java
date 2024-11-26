package com.exemple.demo.mappers;

import org.springframework.stereotype.Component;
import com.exemple.demo.dtos.CreateOPDto;
import com.exemple.demo.dtos.OPDto;
import com.exemple.demo.models.OP;

@Component
public class OPMapper {

    public OPDto toDto(OP op) {
        return new OPDto(
            op.getId(),
            op.getTipo(),
            op.getProduto(),
            op.getNomeProduto(),
            op.getQuantidade(),
            op.getTempoProducao(),
            op.getCustoMPProduto()
        );
    }

    public OP toModel(CreateOPDto opDto) {
        return new OP(
            opDto.tipo(),
            opDto.produto(),
            opDto.quantidade()
        );
    }
}