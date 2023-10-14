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

//toda classe que quero que seja gerenciada
@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    //vamos chamar a nossa interface de usuario para validar se ele existe
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // precismoa definir se a nossa rota que esta sendo buscada sao as tasks
        var serveletPath = request.getServletPath();
        // validando rota
        if (serveletPath.startsWith("/tasks/")){
            //pegar autenticacao (usuario e senha)
            var authorization = request.getHeader("Authorization");

            // VALOR = Basic 281M1092N09n92MB=
            // se colocarmos (5) vai remover ate  o 5, se colocarmos (2,6) vai do 2 ao 3
            // aqui a gente passa a palavra.length para ele saber quantas letras ten
            // poderiamos ter passado o 5 tambem, pega do inicio ate 5
            // o trim remove todos os espaços
            var user_password = authorization.substring("Basic".length()).trim();

            // VALOR = 281M1092N09n92MB=
            // depois de pegarmos somente o valor necessario, vamos fazer um decoder
            // aqui ele ta criando um array de bytes
            byte[] authDecode = Base64.getDecoder().decode((user_password));
            // agora vamos converter nosso array de bites em string
            var authString = new String(authDecode);

            // VALOR = maria:1234
            //agora vamos dar um split para tirar o : para separar user e senha
            String[] credentials = authString.split(":");
            //como ele virou um array a gente pode pegar os indices para acessar os valores
            String username = credentials[0];
            String password = credentials[1];

            // validar usuario
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuario nao autorizado");
            }
            //sele ele existir vamos validar a senha
            else {
                // validar senha
                // passamos a nossa senha e senha do usuario
                // temos que transformar num chararray pq ele espera isso
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                // retorna um booleano - true correro false incorreto
                if (passwordVerify.verified) {
                    // request oq ta vindo da nossa aplicacaoo
                    //response oq temos enviando
                    // recebe o nome e o valor
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
