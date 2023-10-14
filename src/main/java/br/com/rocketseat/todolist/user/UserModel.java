package br.com.rocketseat.todolist.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

//para usarmos getters e setters para todos os atributos -> @Data
//para apenas os getters -> @Getter
//para apenas os setter -> @Setter
//para colocarmos em apenas atributos especifico colocamos o @ em cima do atributo
@Data
//nome da tabela
@Entity(name="tb_users")
public class UserModel {

    @GeneratedValue(generator = "UUID")
    @Id
    private UUID id;

    @Column(name="usuario")
    private String username;

    @Column(name="nome")
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
