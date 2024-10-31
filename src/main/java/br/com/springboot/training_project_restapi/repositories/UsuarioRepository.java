package br.com.springboot.training_project_restapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.training_project_restapi.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query("select u from Usuario u where u.nome like %?1")
    Optional<Usuario> findByNome(String nome);
    
    
    @Query(value = "SELECT u FROM Usuario u WHERE upper(trim(u.nome)) like %?1%")
    List<Usuario> searchByName(String nome);
}
