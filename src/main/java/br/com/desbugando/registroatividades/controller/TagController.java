package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.TagDTO;
import br.com.desbugando.registroatividades.dto.TagPatchDTO;
import br.com.desbugando.registroatividades.service.AtividadeService;
import br.com.desbugando.registroatividades.service.TagService;
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
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private TagService tagService;
    private AtividadeService atividadeService;

    @GetMapping
    public String buscarTodas(Model model) {
        List<TagDTO> tags = tagService.buscarTodas();

        model.addAttribute("activePage", "tags");
        model.addAttribute("item", new TagDTO());
        model.addAttribute("itens", tags);

        return "admin";
    }


    @PostMapping
    public String inserir(@ModelAttribute TagDTO tagDTO,
        RedirectAttributes redirectAttributes) {

        String[] alertaResultado = tagService.inserir(tagDTO);
        redirectAttributes.addFlashAttribute(alertaResultado[0], alertaResultado[1]);

        return "redirect:/tags";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        TagDTO tag = tagService.buscarPorId(id);
        model.addAttribute("item", tag);

        List<AtividadeDTO> atividades = atividadeService.getPorTagId(id);
        model.addAttribute("vinculados", atividades);

        return "admin-detalhes";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> editar(
        @RequestBody TagPatchDTO tagPatchDTO, @PathVariable long id) {
        tagService.patchNome(tagPatchDTO, id);

        return ResponseEntity.ok(Map.of("redirectUrl", "/tags"));
    }
}
