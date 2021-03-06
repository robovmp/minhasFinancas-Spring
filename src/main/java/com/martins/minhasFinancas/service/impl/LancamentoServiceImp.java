package com.martins.minhasFinancas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.martins.minhasFinancas.exception.RegraNegocioException;
import com.martins.minhasFinancas.model.entity.Lancamento;
import com.martins.minhasFinancas.model.enums.StatusLancamento;
import com.martins.minhasFinancas.model.repository.LancamentoRepository;
import com.martins.minhasFinancas.service.LancamentoService;


@Service
public class LancamentoServiceImp implements LancamentoService{

	private LancamentoRepository repository;
	
	@Autowired
	public LancamentoServiceImp( LancamentoRepository repository ) {
		
		this.repository = repository;
		
	}
	
	
	@Override
	@Transactional
	public Lancamento salvar( Lancamento lancamento ) {
		
		validar( lancamento );
		
		return repository.save( lancamento );
		
	}

	@Override
	@Transactional
	public Lancamento atualizar( Lancamento lancamento ) {
		
		validar( lancamento );
		
		lancamento.setStatus( StatusLancamento.PENDENTE );
		
		Objects.requireNonNull( lancamento.getId() );
		
		return repository.save( lancamento );
		
	}

	@Override
	@Transactional
	public void deletar( Lancamento lancamento ) {

		Objects.requireNonNull( lancamento.getId() );
		
		repository.delete( lancamento );
		
	}

	@Override
	@Transactional
	public List<Lancamento> buscar( Lancamento lancamentoFiltro ) {
		
		Example example = Example.of( lancamentoFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher( StringMatcher.CONTAINING ) );
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus( Lancamento lancamento, StatusLancamento status ) {
		 
		lancamento.setStatus( status );
		
		atualizar( lancamento );
		
	}


	@Override
	public void validar(Lancamento lancamento) {

		if( lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals( "" ) ) {
			
			throw new RegraNegocioException( "Informe uma descri????o v??lida." );
			
		}
		
		if( lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12 ) {
			
			throw new RegraNegocioException( "Informe um M??s v??lido." );
			
		}
		
		if( lancamento.getAno()  == null || lancamento.getAno().toString().length() !=4 ) {
			
			throw new RegraNegocioException( "Informe um ano v??lido." );
			
		}
		
		if( lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null ) {
			
			throw new RegraNegocioException( "Informe um Usu??rio." );
			
		}
		
		if( lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1 ) {
			
			throw new RegraNegocioException( "Informe um valor v??lido." );
			
		}
		
		if( lancamento.getTipo() == null ) {
			
			throw new RegraNegocioException( "Informe um tipo de lancamento." );
			
		}
	}

}
