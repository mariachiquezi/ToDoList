package br.com.rocketseat.todolist.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {

    // listar todos os idUser
    List<TaskModel> findByIdUser(UUID idUser);


}
