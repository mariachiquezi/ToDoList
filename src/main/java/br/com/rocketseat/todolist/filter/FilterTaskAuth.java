package br.com.rocketseat.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rocketseat.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var serveletPath = request.getServletPath();
        if (serveletPath.startsWith("/tasks/")){
            var authorization = request.getHeader("Authorization");
            var user_password = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode((user_password));
            // agora vamos converter nosso array de bites em string
            var authString = new String(authDecode);

            // VALOR = maria:1234
            //agora vamos dar um split para tirar o : para separar user e senha
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuario nao autorizado");
            }
            else {
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }

        }else{
            filterChain.doFilter(request, response);
        }

    }
}
