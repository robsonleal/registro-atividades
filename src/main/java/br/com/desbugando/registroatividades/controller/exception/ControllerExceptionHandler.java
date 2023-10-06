package br.com.desbugando.registroatividades.controller.exception;

import br.com.desbugando.registroatividades.service.exception.BusinessException;
import br.com.desbugando.registroatividades.service.exception.DataBaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> entityNotFound(BusinessException e,
        HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Business Exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataBaseException.class)
    public String databaseException(DataBaseException e, RedirectAttributes redirectAttributes) {
        String mensagemErro;
        if (e.getMessage().contains("Duplicate entry"))
            mensagemErro = "Atividade já cadastrada!";
        else
            mensagemErro = "Erro ao salvar atividade!";

        redirectAttributes.addFlashAttribute("mensagemErro", mensagemErro);

        return "redirect:/atividades/criar";
    }
}
