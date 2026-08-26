package br.com.mv.spring_boot_essentials.controller;

import br.com.mv.spring_boot_essentials.database.model.ProdutoEntity;
import br.com.mv.spring_boot_essentials.dto.ProdutoDto;
import br.com.mv.spring_boot_essentials.exception.NotFoundException;
import br.com.mv.spring_boot_essentials.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/Produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoEntity>> findAll() {
        List<ProdutoEntity> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity createproduct(@RequestBody ProdutoDto produtoDto){
        return produtoService.createProduct(produtoDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity updateProduct(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) throws NotFoundException {
        return produtoService.atualizarProduto(produtoDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        produtoService.removerProduto(id);
    }

}
