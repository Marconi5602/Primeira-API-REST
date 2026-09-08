package br.com.mv.spring_boot_essentials.service;

import br.com.mv.spring_boot_essentials.database.model.AlunosEntity;
import br.com.mv.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.mv.spring_boot_essentials.database.model.TreinosEntity;
import br.com.mv.spring_boot_essentials.database.repository.IAlunosRepository;
import br.com.mv.spring_boot_essentials.database.repository.IAvaliacoesFisicasRepository;
import br.com.mv.spring_boot_essentials.database.repository.ITreinosRepository;
import br.com.mv.spring_boot_essentials.dto.AlunoDto;
import br.com.mv.spring_boot_essentials.exception.BadRequestException;
import br.com.mv.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunosService {

    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;
    private final ITreinosRepository treinosRepository;
    private final IAlunosRepository alunosRepository;

    public void criarAluno(AlunoDto alunoDto) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(alunoDto.getEmail())
                .orElse(null);

        if (aluno != null){
            throw new BadRequestException("Aluno já cadastrado com este email");
        }

        alunosRepository.save(AlunosEntity.builder()
                        .nome(alunoDto.getNome())
                        .email(alunoDto.getEmail())
                .build());
    }

    public AvaliacoesFisicasEntity getAlunoAvaliacao(Integer alunoId) throws NotFoundException {
        AlunosEntity aluno = alunosRepository.findByIdFetch(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado."));

        AvaliacoesFisicasEntity avaliacao = aluno.getAvaliacaoFisica();
        if (avaliacao == null){
            throw new NotFoundException("Avaliação física não encontrada para este aluno");
        }
        return avaliacao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletarAluno(Integer alunoId) throws NotFoundException {
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado."));

        List<Integer> treinosAlunoIds = aluno.getTreinos().stream()
                .map(TreinosEntity::getId)
                .toList();

        treinosRepository.deleteAllById(treinosAlunoIds);

        alunosRepository.deleteById(alunoId);

        avaliacoesFisicasRepository.deleteById(aluno.getAvaliacaoFisica().getId());
    }
}
