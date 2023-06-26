package com.alurafood.pagamentos.controllers;

import com.alurafood.pagamentos.dtos.PagamentoDTO;
import com.alurafood.pagamentos.enums.Status;
import com.alurafood.pagamentos.services.PagamentoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable @NotNull Long id) {
        PagamentoDTO dto = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criar(@RequestBody @NotNull PagamentoDTO dto) {
        return ResponseEntity.ok(pagamentoService.criarPagamento(dto));
    }

    @PutMapping
    public ResponseEntity<PagamentoDTO> atualizar(@RequestBody @NotNull PagamentoDTO dto) {
        return ResponseEntity.ok(pagamentoService.atualizarPagamento(dto.getId(), dto));
    }

    @DeleteMapping("/{id}")
    public void deletar(long id) {
        pagamentoService.delete(id);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id) {
        this.pagamentoService.confimarPedido(id);
    }

    public void pagamentoAutorizadoComIntegracaoPendente(@PathVariable @NotNull Long id, Exception e) {
        this.pagamentoService.alterarStatus(id);
    }

}
