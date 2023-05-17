package com.alurafood.pagamentos.services;

import com.alurafood.pagamentos.dtos.PagamentoDTO;
import com.alurafood.pagamentos.enums.Status;
import com.alurafood.pagamentos.interfaces.PedidosClient;
import com.alurafood.pagamentos.models.Pagamento;
import com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidosClient pedidosClient;

    public Page<PagamentoDTO> obterTodos(Pageable pageable) {
        return this.pagamentoRepository
                .findAll(pageable)
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = this.pagamentoRepository
                .findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento = this.pagamentoRepository.save(pagamento);
        return modelMapper
                .map(pagamento, PagamentoDTO.class);

    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento.setStatus(Status.CRIADO);
        pagamento = this.pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);

    }

    public void delete(Long id) {
        this.pagamentoRepository.deleteById(id);
    }

    public void confimarPedido(Long id){
        Pagamento pagamento = pagamentoRepository.findById(id)
                                        .orElseThrow(EntityNotFoundException::new);
       
       pagamento.setStatus(Status.CONFIRMADO);  
       this.pagamentoRepository.save(pagamento);
       this.pedidosClient.atualizar(pagamento.getPedidoId());                                

    }

}
