package com.cadastral.controllers;

import com.cadastral.entidy.Usuario;
import com.cadastral.repository.UsuarioRepository;
import com.cadastral.utils.HandlerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

import static com.cadastral.filters.AuthFilter.LOGIN_PATH;
import static com.cadastral.utils.Utils.encode;
import static com.cadastral.utils.Utils.mountToken;

@RestController
public class AuthorizationController {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "api-cadastral/logar", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> login() {
        String path = request.getScheme()
                .concat("://")
                .concat(request.getServerName())
                .concat(":")
                .concat(String.valueOf(request.getServerPort()))
                .concat(LOGIN_PATH);
        return ResponseEntity.ok(path).status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping(value = "api-cadastral/logar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logar(@RequestBody Usuario usuario) {

        if (usuario == null || usuario.getUsername() == null || usuario.getSenha() == null) {
            return ResponseEntity.ok(new HandlerResult("Informe o username e senha do usuário", "Autorização"));
        }

        Usuario usuarioCadastrado = repository.findByUsernameAndSenha(usuario.getUsername(), encode(usuario.getSenha()));
        if (usuarioCadastrado == null) {
            return ResponseEntity.ok(new HandlerResult("Usuário e senha não cadastrados", "Autorização"));
        }

        return ResponseEntity.ok(new Credentials(usuarioCadastrado));
    }

    private static class Credentials implements Serializable {
        private Usuario usuario;
        private String token;
        private Credentials(){}
        Credentials(Usuario usuario) {
            this.token = mountToken(usuario.getUsername(), usuario.getSenha());
            usuario.setSenha(null); // limpa o password para não ficar o dado vulnerável no browser
            this.usuario = usuario;
        }
        public Usuario getUsuario() {
            return usuario;
        }
        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }
        public String getToken() {
            return token;
        }
    }

}
