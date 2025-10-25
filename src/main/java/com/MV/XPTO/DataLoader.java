package com.MV.XPTO;

import com.MV.XPTO.model.Cliente;
import com.MV.XPTO.model.Conta;
import com.MV.XPTO.model.Transacao;
import com.MV.XPTO.model.enums.PersonType;
import com.MV.XPTO.model.enums.TransactionType;
import com.MV.XPTO.repository.ClienteRepository;
import com.MV.XPTO.repository.ContaRepository;
import com.MV.XPTO.repository.TransacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public DataLoader(ClienteRepository clienteRepository, ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if  (clienteRepository.count() == 0) {

            Cliente cliente = new Cliente();

            cliente.setNome("Thiago pereira");
            cliente.setPersonType(PersonType.PF);
            cliente.setCpf("39618404613");
            clienteRepository.save(cliente);

            Conta conta = new Conta();
            conta.setNomeBanco("C1");
            conta.setAgencia("0001");
            conta.setNumeroConta("11223344-5");
            conta.setCliente(cliente);
            contaRepository.save(conta);

            Transacao transacao = new Transacao();
            transacao.setTipoTransacao(TransactionType.CREDIT);
            transacao.setTotal(new BigDecimal("1000.00"));
            transacao.setConta(conta);
            transacaoRepository.save(transacao);

            System.out.println("Dados iniciais criados");
        }
    }
}