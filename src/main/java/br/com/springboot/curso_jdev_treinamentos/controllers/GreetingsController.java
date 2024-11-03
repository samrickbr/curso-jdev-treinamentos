package br.com.springboot.curso_jdev_treinamentos.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamentos.model.Usuario;
import br.com.springboot.curso_jdev_treinamentos.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired /* Injeção de dependencias - IC, CD ou CDI */
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	/*
	 * @RequestMapping(value = "/{name}", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String greetingText(@PathVariable
	 * String name) { return "Bem vindo ao Curso de Spring Boot " + name + "!"; }
	 */

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome, int idade) {

		Usuario usuario = new Usuario(); /* instancio o usuario/objeto */
		usuario.setNome(nome); /* defino/set a propriedade que vou usar */
		usuario.setIdade(idade);

		usuarioRepository.save(usuario); /* salvo no bd o objeto instanciado */

		return "Olá Mundo " + nome + "!";
	}

	/*
	 * Criar o método para listar todos os usuarios do BD metodo: uma Lista do
	 * ResponseEntity com o Model Usuario para uma lista de usuarios
	 * 
	 * Cria a Lista com o Model(Classe) usuarios setando o usuarioRepository e
	 * buscando todos
	 * 
	 * retorna uma nova RespondseEntity com a Lista da classe retornando os usuario
	 * encontrados e HttpStatus de conclusão
	 * 
	 * Fazer o GetMapping com o parametro de listagem - API Usar o ResponseBody para
	 * indicar onde retorna os dados
	 *
	 */

	@GetMapping(value = "listartodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	/*
	 * Criar método/APi para salvar o usuario por POST 1 - Mapear a URL para salvar
	 * 2 - Fazer o retorno da resposta
	 * 
	 * Método: Chamar o ResponseEntity com a classe usuario para Salvar passando o
	 * RequestBody da Classe Instanciar a classe para salvar o usuario do repository
	 * Retornar a nova Entidade da classe usuario com o novo usuario e o status
	 * Created.
	 * 
	 */

	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	/*
	 * Método de deletar usuario por ID Mapear a URL Retornar a resposta Requisitar
	 * os dados para deletar
	 * 
	 * Retornar a mensagem de conclusão da tarefa
	 * 
	 */

	@DeleteMapping(value = "deletar")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long iduser) {
		usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
	}

	/* Método para buscar por ID */

	@GetMapping(value = "buscarporid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarporid(@RequestParam(name = "iduser") Long iduser) {
		Usuario usuario = usuarioRepository.findById(iduser).get();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	/* Método para buscar por Nome */

	@GetMapping(value = "buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}

	/* Método para atualizar usuario por ID */

	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		/* Validação para nao deixar o Id em branco e cadastrar novo */
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("O ID deve ser informado!", HttpStatus.CONFLICT);
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

}
