package br.com.mv.spring_boot_essentials.controller;

import br.com.mv.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.mv.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.mv.spring_boot_essentials.dto.AlunoDto;
import br.com.mv.spring_boot_essentials.dto.ExercicioDto;
import br.com.mv.spring_boot_essentials.exception.BadRequestException;
import br.com.mv.spring_boot_essentials.exception.NotFoundException;
import br.com.mv.spring_boot_essentials.service.AlunosService;
import br.com.mv.spring_boot_essentials.service.ExerciciosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
public class AlunosController {

    private final AlunosService alunosService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAluno(@Valid @RequestBody AlunoDto alunoDto) throws BadRequestException {
        alunosService.criarAluno(alunoDto);
    }

    @GetMapping("/{alunoId}/avaliacao")
    public AvaliacoesFisicasEntity getAvaliacaoFisica(@PathVariable Integer alunoId) throws NotFoundException {
        return alunosService.getAlunoAvaliacao(alunoId);
    }

    @DeleteMapping("/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerAluno(@PathVariable Integer alunoId) throws NotFoundException {
        alunosService.deletarAluno(alunoId);
    }
}
