package com.MV.XPTO;

import com.MV.XPTO.model.Cliente;
import com.MV.XPTO.model.enums.PersonType;
import com.MV.XPTO.service.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

   private final ClienteService clienteService;

   public DataLoader(ClienteService clienteService) {
       this.clienteService = clienteService;
   }

   @Override
   public void run(String... args) {
       if (clienteService.findAll().isEmpty()) {
           Cliente cliente = new Cliente();
           cliente.setNome("Thiago Pereira");
           cliente.setPersonType(PersonType.PF);
           cliente.setCpf("39618404613");

           clienteService.criarCiente(cliente);

           System.out.println("Cliente e conta inicial criados com sucesso!");
       }
   }
}