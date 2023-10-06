package br.com.desbugando.registroatividades.controller;

import br.com.desbugando.registroatividades.dto.AtividadeDTO;
import br.com.desbugando.registroatividades.dto.CategoriaDTO;
import br.com.desbugando.registroatividades.dto.MovimentoDTO;
import br.com.desbugando.registroatividades.dto.TagDTO;
import br.com.desbugando.registroatividades.service.AtividadeService;
import br.com.desbugando.registroatividades.service.CategoriaService;
import br.com.desbugando.registroatividades.service.TagService;
import br.com.desbugando.registroatividades.service.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/atividades")
@AllArgsConstructor
public class AtividadeController {

    private AtividadeService service;
    private TagService tagService;
    private CategoriaService categoriaService;

    @GetMapping
    public String getNaoConcluidas(Model model) {
        List<AtividadeDTO> atividades = service.getNaoConcluidas();

        model.addAttribute("atividades", atividades);
        model.addAttribute("activePage", "atividades");

        return "atividades";
    }

    @GetMapping("/criar")
    public String criar(Model model) {
        List<CategoriaDTO> categorias = categoriaService.buscarTodas();
        model.addAttribute("categorias", categorias);

        List<TagDTO> tags = tagService.buscarTodas();
        model.addAttribute("tags", tags);

        model.addAttribute("atividadeDTO", new AtividadeDTO());
        model.addAttribute("activePage", "cadastro");

        return "cadastro";
    }

    @PostMapping("/criar")
    public String createAtividade(@ModelAttribute AtividadeDTO atividadeDTO, RedirectAttributes redirectAttributes) {
        String[] alertaResultado = service.insert(atividadeDTO);
        redirectAttributes.addFlashAttribute(alertaResultado[0], alertaResultado[1]);

        return "redirect:/atividades";
    }

    @GetMapping("/historico")
    public String historico(Model model) {
        Instant dataFinal = Instant.now();
        Instant dataInicial = dataFinal.minus(Duration.ofDays(7));

        if (dataInicial.isAfter(dataFinal)) {
            throw new BusinessException("Data inicial, precisa ser menor que a data final!");
        }

        List<AtividadeDTO> atividades = service.getConcluidasNoPeriodo(dataInicial, dataFinal);
        model.addAttribute("atividades", atividades);
        model.addAttribute("activePage", "historico");

        return "historico";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        AtividadeDTO atividade = service.findById(id);
        model.addAttribute("atividade", atividade);
        model.addAttribute("activePage", "detalhesAtividades");

        return "detalhes";
    }

    @GetMapping("/{id}/editar")
    public String editar(Model model, @PathVariable long id) {
        List<CategoriaDTO> categorias = categoriaService.buscarTodas();
        model.addAttribute("categorias", categorias);
        List<TagDTO> tags = tagService.buscarTodas();
        model.addAttribute("tags", tags);
        AtividadeDTO atividade = service.findById(id);
        model.addAttribute("atividade", atividade);
        return "editar";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@ModelAttribute AtividadeDTO atividadeDTO, @PathVariable long id) {
        service.editar(atividadeDTO);

        return "redirect:/atividades/" + id;
    }

    @GetMapping("/{id}/movimentar")
    public String trabalhar(@PathVariable Long id, Model model) {
        AtividadeDTO atividade = service.findById(id);
        model.addAttribute("atividade", atividade);

        MovimentoDTO movimento = new MovimentoDTO();
        movimento.setRegistroInicio(Instant.now());
        model.addAttribute("movimento", movimento);

        return "movimentar";
    }

    @PostMapping("/{id}/concluir")
    public String concluir(@PathVariable Long id) {
        service.concluir(id);

        return "redirect:/atividades/historico";
    }

    @PostMapping("/{id}/ativar")
    public String ativar(@PathVariable Long id) {
        service.ativar(id);

        return "redirect:/atividades";
    }
}
