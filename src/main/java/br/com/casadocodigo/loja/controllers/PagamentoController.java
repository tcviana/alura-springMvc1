package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;
import br.com.casadocodigo.loja.models.Usuario;

@Controller
@RequestMapping("/pagamento")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;
	
	// utilização do Callable para utilização de thread com result de exception
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, 
			RedirectAttributes model) {
		String uri = "http://book-payment.herokuapp.com/payment";
		return() -> {
			try {
				String response = 
						restTemplate.postForObject(uri, 
								new DadosPagamento(carrinho.getTotal()), String.class);
				System.out.println(response);
				enviaEmailCompraProduto(usuario);
				model.addFlashAttribute("sucesso",response);		
				return new ModelAndView("redirect:/produtos");
			}catch (Exception e) {
				e.printStackTrace();
				model.addFlashAttribute("falha","Valor maior que o permitido.");
				return new ModelAndView("redirect:/produtos");
			}
			
			
		};		
	}
	
	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("no-reply@seuserver.com");//#ToDo substitua por email válido, o mesmo do AppConf ServerMail
		email.setTo(usuario.getEmail());
		email.setSubject("Compra realizada "+usuario.getEmail());
		email.setText("Compra realizada com sucesso no valor de "+carrinho.getTotal());
		
		sender.send(email);
	}
}
