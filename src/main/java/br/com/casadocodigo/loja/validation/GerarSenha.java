package br.com.casadocodigo.loja.validation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {

    public static void main(String[] args) {    
        String senhaCriptografado = new BCryptPasswordEncoder().encode("123");
        System.out.println(senhaCriptografado);
    }
}
