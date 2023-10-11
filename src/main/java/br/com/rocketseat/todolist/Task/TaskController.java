package br.com.rocketseat.todolist.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    //para armazenar o taskmodel
    @Autowired
    private ITaskRepository taskRepository;

    //vamos acessar pelo post entao vamos definir o postmap
    @PostMapping("/")
    public TaskModel create(@RequestBody TaskModel taskModel){
        //chamando o repositorio e criando uma task
        var task = this.taskRepository.save(taskModel);
        return task;
    }

}
