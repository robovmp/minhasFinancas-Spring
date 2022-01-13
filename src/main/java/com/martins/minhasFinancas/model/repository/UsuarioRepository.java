package com.martins.minhasFinancas.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.martins.minhasFinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository< Usuario, Long > {
	
	/*Optional<Usuario> findByEmail( String email ); Forma didática */
	
	boolean existsByEmail( String email );
	
	public Usuario findByEmail( String email );

}
