package com.MV.XPTO.service;

import com.MV.XPTO.model.Conta;
import com.MV.XPTO.model.Transacao;
import com.MV.XPTO.model.enums.TransactionType;
import com.MV.XPTO.repository.ContaRepository;
import com.MV.XPTO.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    @Transactional
    public Transacao createTransacao(Transacao transacao) {
        Conta conta = contaRepository.findById(transacao.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (transacao.getTipoTransacao() == TransactionType.CREDIT) {
            conta.setSaldo(conta.getSaldo().add(transacao.getTotal()));
        }

        else {
            conta.setSaldo(conta.getSaldo().subtract(transacao.getTotal()));
        }

        contaRepository.save(conta);
        return transacaoRepository.save(transacao);
    }
}
