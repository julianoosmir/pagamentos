package com.alurafood.pagamentos.models;

import java.math.BigDecimal;

import com.alurafood.pagamentos.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(max = 19)
    private String numero;

    @NotBlank
    @Size(max = 7)
    private String expiracao;

    @NotBlank
    @Size(max = 3, min = 3)
    private String codigo;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Long pedidoId;
    
    @NotNull
    private Long formaPagamentoId;

}
