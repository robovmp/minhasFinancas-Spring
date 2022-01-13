package com.martins.minhasFinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.martins.minhasFinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
