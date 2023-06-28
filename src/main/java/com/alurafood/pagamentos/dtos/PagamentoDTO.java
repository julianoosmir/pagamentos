package com.alurafood.pagamentos.dtos;

import java.math.BigDecimal;

import com.alurafood.pagamentos.enums.Status;

import lombok.Data;

@Data
public class PagamentoDTO {
    private long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long pedidoId;
    private Long formaPagamentoId;

}
