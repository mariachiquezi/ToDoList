package br.com.rocketseat.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    // as informações do nosso usuario vai vir dentro do body
    // por isso a anotacao
    @PostMapping("/")
    public void create (@RequestBody UserModel userModel){
        System.out.println(userModel.name);

    }
}
