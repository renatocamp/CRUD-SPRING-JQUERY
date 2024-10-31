package br.com.springboot.training_project_restapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.training_project_restapi.model.Usuario;
import br.com.springboot.training_project_restapi.repositories.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping(value = "/signup")
	@ResponseBody
	public ResponseEntity<?> saveUser(@RequestBody Usuario usuario) {
		
		 Optional<Usuario> existingUser = repository.findByNome(usuario.getNome());
		 
		    if (existingUser.isPresent()) {
		        return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Usuário já existente");
		    }
		Usuario user = repository.save(usuario);
		return new ResponseEntity<Object>(user, HttpStatus.CREATED);
		
	}
	
    @GetMapping(value = "/listatodos")
    @ResponseBody // RETORNA OS DADOS PARA O CORPO DA RESPOSTA
    public ResponseEntity<List<Usuario>> userList(){
    	List<Usuario> lista = repository.findAll();
    	return new ResponseEntity<List<Usuario>>(lista, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestParam Long userId){
    	
    	Optional<Usuario> usuario = repository.findById(userId);
    	
    	if(usuario.isPresent()) {
    		repository.delete(usuario.get());
    		return new ResponseEntity<String>("Usuário deletado com Sucesso!", HttpStatus.OK);
    	}
    	return new ResponseEntity<String>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(value = "/buscausuario")
    @ResponseBody
    public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "userId")Long userId){
    	
    	Optional<Usuario> usuario = repository.findById(userId);
    	
    	if(usuario.isPresent()) {
    		return new ResponseEntity<Usuario>(HttpStatus.OK).of(usuario);
    	}
    	return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario) {

        if (usuario.getId() == null) {
            return new ResponseEntity<String>("ID não informado para atualização",HttpStatus.NOT_FOUND);
        }

        Usuario userUsuario = repository.saveAndFlush(usuario);

        return new ResponseEntity<>(userUsuario, HttpStatus.OK);
    }

    @GetMapping(value = "/buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String nome){
    	
    	List<Usuario> userList = repository.searchByName(nome.trim().toUpperCase());
    	
    	if(userList.isEmpty()) {
    		return new ResponseEntity<List<Usuario>>(userList, HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<List<Usuario>>(userList, HttpStatus.OK);
    }
    
    

}
