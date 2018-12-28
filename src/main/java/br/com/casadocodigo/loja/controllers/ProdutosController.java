package br.com.casadocodigo.loja.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos") // insere o prefixo em todas as requisições
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO dao;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("form")
	public ModelAndView form() {
		ModelAndView modelView = new ModelAndView("produtos/form");
		modelView.addObject("tipos",TipoPreco.values());
		return modelView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes){
		if (result.hasErrors()) {
			return form();
		}
		System.out.println(produto);
		dao.gravar(produto);
		/**
		 * Atributos do tipo Flash têm uma particularidade que é interessante observar. 
		 * Eles só duram até a próxima requisição, ou seja, transportam informações de uma 
		 * requisição para a outra e, então, deixam de existir. (usando o código de status 302).
		 */
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		/**
		 * ao fazer F5 o navegador repete o ultimo request que ele realizou, 
		 * e quando esse resquest é um POST, todos os dados que foram enviados 
		 * também são repetidos. Se você realizou um insert no banco de dados, 
		 * esse insert será repetido. Ou mesmo se realizou alguma operações que 
		 * envia e-mail, por exemplo, o e-mail será enviado duas vezes ao pressionar F5.
		 * Para evitar qualquer problema de dados reenviados, 
		 * realizamos um redirect após um formulário com POST.
		 */
		ModelAndView modelAndView = new ModelAndView("redirect:produtos");
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView modelView = new ModelAndView("produtos/listar");
		modelView.addObject("produtos", dao.listar());
		return modelView;
	}
}
