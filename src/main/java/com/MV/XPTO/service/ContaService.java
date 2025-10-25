package com.MV.XPTO.service;

import com.MV.XPTO.model.Conta;
import com.MV.XPTO.repository.ContaRepository;
import com.MV.XPTO.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, TransacaoRepository transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    public Conta findById(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    @Transactional
    public void deleteConta(Long id) {
        if (transacaoRepository.findByContaId(id).isEmpty()) {
            throw new  RuntimeException("Não é possível excluir uma conta com movimentações");
        }
        contaRepository.deleteById(id);
    }

    public List<Conta> findByCliente(Long clientId) {
        return contaRepository.findByClienteId(clientId);
    }
}
