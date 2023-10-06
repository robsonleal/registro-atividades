package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.dto.CategoriaPatchDTO;
import br.com.desbugando.registroatividades.service.AtividadeService;
import br.com.desbugando.registroatividades.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {
    private CategoriaService categoriaService;
    private AtividadeService atividadeService;

    @GetMapping
    public String buscarTodas(Model model) {
        List<CategoriaDTO> categorias = categoriaService.buscarTodas();

        model.addAttribute("activePage", "categorias");
        model.addAttribute("item", new CategoriaDTO());
        model.addAttribute("itens", categorias);

        return "admin";
    }

    @PostMapping
    public String inserir(@ModelAttribute CategoriaDTO categoriaDTO,
        RedirectAttributes redirectAttributes) {

        String[] alertaResultado = categoriaService.inserir(categoriaDTO);
        redirectAttributes.addFlashAttribute(alertaResultado[0], alertaResultado[1]);

        return "redirect:/categorias";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        CategoriaDTO categoria = categoriaService.buscarPorId(id);
        model.addAttribute("item", categoria);

        List<AtividadeDTO> atividades = atividadeService.getPorCategoriaId(id);
        model.addAttribute("vinculados", atividades);

        return "admin-detalhes";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> editar(
        @RequestBody CategoriaPatchDTO categoriaPatchDTO, @PathVariable long id) {
        categoriaService.patchNome(categoriaPatchDTO, id);

        return ResponseEntity.ok(Map.of("redirectUrl", "/categorias"));
    }
}
