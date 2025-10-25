package com.MV.XPTO.service;

import com.MV.XPTO.model.Cliente;
import com.MV.XPTO.model.Conta;
import com.MV.XPTO.model.Transacao;
import com.MV.XPTO.model.enums.TransactionType;
import com.MV.XPTO.repository.ClienteRepository;
import com.MV.XPTO.repository.ContaRepository;
import com.MV.XPTO.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ClienteService(ClienteRepository  clienteRepository, ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente criarCiente(Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.save(cliente);

        Conta conta = new Conta();
        conta.setCliente(clienteSalvo);
        conta.setNomeBanco("C1");
        conta.setAgencia("0001");
        conta.setNumeroConta("Number" + cliente.getId());
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setTipoTransacao(TransactionType.CREDIT);
        transacao.setTotal(new BigDecimal("100.00"));
        transacao.setDescrisao("Depósito inicial");
        transacaoRepository.save(transacao);

        conta.setSaldo(conta.getSaldo().add(transacao.getTotal()));
        contaRepository.save(conta);

        return clienteSalvo;
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
