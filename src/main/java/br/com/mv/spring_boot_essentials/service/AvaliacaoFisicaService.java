package br.com.mv.spring_boot_essentials.service;

import br.com.mv.spring_boot_essentials.database.model.AlunosEntity;
import br.com.mv.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.mv.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.mv.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.mv.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.mv.spring_boot_essentials.database.repository.IExerciciosRepository;
import br.com.mv.spring_boot_essentials.dto.AvaliacaoFisicaDto;
import br.com.mv.spring_boot_essentials.dto.AvaliacoesFisicasProjection;
import br.com.mv.spring_boot_essentials.dto.ExercicioDto;
import br.com.mv.spring_boot_essentials.exception.BadRequestException;
import br.com.mv.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {
    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void criarAvaliacaoFisica(AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDto.getAlunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        AvaliacoesFisicasEntity avaliacaoFisica = aluno.getAvaliacaoFisica();
        if (avaliacaoFisica != null) {
            throw new BadRequestException("Avaliação fisica já cadastrada para este aluno.");
        }

        avaliacaoFisica = AvaliacoesFisicasEntity.builder()
                .peso(avaliacaoFisicaDto.getPeso())
                .altura(avaliacaoFisicaDto.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDto.getPorcentagemGorduraCorporal())
                .build();

        aluno.setAvaliacaoFisica(avaliacaoFisica);
        alunosRepository.save(aluno);
    }

    public List<AvaliacoesFisicasProjection> getAllAvaliacoes(){
        return avaliacoesFisicasRepository.getAllAvaliacoes();
    }

    public Page<AvaliacoesFisicasProjection> getAllAvaliacoesPageable(Integer page, Integer size){
        return avaliacoesFisicasRepository.getAllAvaliacoesPage(PageRequest.of(page, size));
    }
}
