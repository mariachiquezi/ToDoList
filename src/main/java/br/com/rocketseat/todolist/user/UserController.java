package br.com.rocketseat.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/user")
public class UserController {

    // instanciando nossa interface
    @Autowired
    private IUserRepository userRepository;


    // vamos usar om ResponseEntity para termos diferentes tipos de retorno
    // estamos salvando o usuariio
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        //vamos criptografar nossa senha antes de retornar o usuario
        // o 12 passamos por padrao, depois passamos oq queremos criptografar
        // o tochar transforma num array de caracteres
        var passwordHashred  = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        //atribuindo valor
        userModel.setPassword(passwordHashred);
        var userCreate = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreate);
    }
}
