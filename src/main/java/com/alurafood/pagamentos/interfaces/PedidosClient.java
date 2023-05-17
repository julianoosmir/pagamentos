package com.alurafood.pagamentos.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("pedidos-ms")
public interface PedidosClient {
    @RequestMapping(method = RequestMethod.PUT,value = "/pedidos/{id}/pago")
    void atualizar(@PathVariable Long id);
}
