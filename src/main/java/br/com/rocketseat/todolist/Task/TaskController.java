package br.com.rocketseat.todolist.Task;

import br.com.rocketseat.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    //para armazenar o taskmodel
    @Autowired
    private ITaskRepository taskRepository;

    //vamos acessar pelo post entao vamos definir o postmap
    @PostMapping("/")
    // temos que passar esse http para pegar o idUser no nosso filtrer
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
       var idUser = request.getAttribute("idUser");
        //vamos setar nosso idUser dentro do nosso taskmodel
        taskModel.setIdUser((UUID)idUser);

        //validando a data
        var currentDate = LocalDateTime.now();
        // se a data de start for maior que a data atual
        if (currentDate.isAfter(taskModel.getStartAt())  || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/termino deve ser maior que a data atual");
        }
        //validando se a minha data de inicio é depois do fim
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data de termino");
        }
        //chamando o repositorio e criando uma task
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        //pegando o idUser
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    //receber informações
    //precisamos passar o id para saber a tasks q vai editar
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        //buscar no banco de dados, o id da task
        // se nao retornar nada ele retorna null
        var task = this.taskRepository.findById(id).orElse( null);
        // passsar nosso source e nosso target
        Utils.copyNonNullProperties(taskModel, task);

        return this.taskRepository.save(task);
    }



}
