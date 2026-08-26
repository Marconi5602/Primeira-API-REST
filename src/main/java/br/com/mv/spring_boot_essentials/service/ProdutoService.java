package br.com.mv.spring_boot_essentials.service;


import br.com.mv.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.mv.spring_boot_essentials.dto.ProdutoDto;
import br.com.mv.spring_boot_essentials.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.apache.commons.lang3.ObjectUtils.max;

@Service
public class ProdutoService {
    private static final List<ProdutoEntity> PRODUTOS = new ArrayList<>();
         static{
             PRODUTOS.add(ProdutoEntity.builder()
                     .id(1L)
                     .nome("Notebook")
                     .preco(new BigDecimal(5000))
                     .quantidade(10)
                     .build());

             PRODUTOS.add(ProdutoEntity.builder()
                     .id(2L)
                     .nome("Celular")
                     .preco(new BigDecimal(6000))
                     .quantidade(10)
                     .build());

             PRODUTOS.add(ProdutoEntity.builder()
                     .id(3L)
                     .nome("mouse")
                     .preco(new BigDecimal(500))
                     .quantidade(10)
                     .build());
         }


    public List<ProdutoEntity> findAll(){
        return new ArrayList<>(PRODUTOS);
    }

    public ProdutoEntity createProduct(ProdutoDto produtoDto){

        long Identificador = PRODUTOS.stream()
                .mapToLong(ProdutoEntity::getId)
                .max()
                .orElse(0) + 1;

        ProdutoEntity novoProduto = ProdutoEntity.builder()
                .id(Identificador)
                .nome(produtoDto.getNome())
                .preco(produtoDto.getPreco())
                .quantidade(produtoDto.getQuantidade())
                .build();

        PRODUTOS.add(novoProduto);

        return novoProduto;
    }

    public ProdutoEntity atualizarProduto(ProdutoDto produtoDto, Long id) throws NotFoundException {
        ProdutoEntity produto = PRODUTOS.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Não encontrado"));

        produto.setNome(produtoDto.getNome());
        produto.setPreco(produtoDto.getPreco());
        produto.setQuantidade(produtoDto.getQuantidade());

        return produto;
    }

    public void removerProduto(Long id){
        PRODUTOS.removeIf(p -> p.getId().equals(id));
    }
}
