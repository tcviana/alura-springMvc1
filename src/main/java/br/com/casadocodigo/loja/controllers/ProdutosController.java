package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("produtos") // insere o prefixo em todas as requisições
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;

	@RequestMapping("form")
	public ModelAndView form() {
		ModelAndView modelView = new ModelAndView("produtos/form");
		modelView.addObject("tipos",TipoPreco.values());
		return modelView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String gravar(Produto produto){
		System.out.println(produto);
		dao.gravar(produto);
		return "/produtos/ok";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView modelView = new ModelAndView("produtos/listar");
		modelView.addObject("produtos", dao.listar());
		return modelView;
	}
}
