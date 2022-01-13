package com.martins.minhasFinancas.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.martins.minhasFinancas.exception.ErroAutenticacao;
import com.martins.minhasFinancas.exception.RegraNegocioException;
import com.martins.minhasFinancas.model.entity.Usuario;
import com.martins.minhasFinancas.model.repository.UsuarioRepository;
import com.martins.minhasFinancas.service.UsuarioService;

import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;
	
	@Autowired
	public UsuarioServiceImpl( UsuarioRepository repository ) {
		super();
		this.repository = repository;
	}
	
	@Override
	public Usuario autenticar ( String email, String senha ) {
		Usuario usuario = repository.findByEmail( email );
		
		if( usuario.getEmail() == null ) {
			throw new ErroAutenticacao( "Usuário não encontrado para o email informado." );
		}
		
		if( !usuario.getSenha().equals(senha) ) {
			throw new ErroAutenticacao( "Senha inválida." );
		}
		
		return usuario;
	}

	@Override
	@Transactional
	public Usuario salvarUsuario ( Usuario usuario ) {
		validarEmail( usuario.getEmail() );
		return repository.save( usuario );
	}

	@Override
	public void validarEmail ( String email ) {
		boolean existe = repository.existsByEmail( email );
		if( existe ) {
			throw new RegraNegocioException( "Já existe um usuário cadastrado com este email." );
		}
	}
	
	

}
