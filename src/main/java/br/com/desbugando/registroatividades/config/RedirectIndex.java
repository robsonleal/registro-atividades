package br.com.desbugando.registroatividades.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectIndex {
    @GetMapping("/")
    public String index() {
        return "redirect:/atividades";
    }
}
