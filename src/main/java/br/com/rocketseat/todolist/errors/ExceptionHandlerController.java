package br.com.rocketseat.todolist.errors;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//vamos criar a customizacao do nosso erro
// controller adivice define as classes globais nos tratamento de erros
// tada excessao vai passar pora q
@ControllerAdvice
public class ExceptionHandlerController {

    // informar o tipo de exception que vai cair nesse metodo
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String>handlerHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }
}
