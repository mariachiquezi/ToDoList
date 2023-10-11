package br.com.rocketseat.todolist.Task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class TaskModel {

    /*
    Uma task pode ter
    Id
    Usuario
    Titulo
    Data Inicio
    Data Termino
    Descrição
    Prioridade
     */

    @GeneratedValue(generator = "UUID")
    @Id
    private UUID id;

    //FK
    private UUID idUser;

    //limitar a quantidade de caracteres
    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    //para mostrar quando foi criado no banco de dados
    @CreationTimestamp
    private LocalDateTime createAt;
}
