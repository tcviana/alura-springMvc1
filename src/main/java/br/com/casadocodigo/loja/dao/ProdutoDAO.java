package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Repository // gerenciamento do spring, por exemplo para auto create
@Transactional // opera com transações
public class ProdutoDAO {
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		//manager.getTransaction().begin();
		manager.persist(produto);
		//manager.getTransaction().commit();
	}
	
	public List<Produto> listar(){
		return manager.createQuery("select p from Produto p ", Produto.class).getResultList();
	}

}
