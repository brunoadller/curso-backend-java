package com.dtoproject.projectdto.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtoproject.projectdto.model.Produto;
import com.dtoproject.projectdto.services.ProdutoService;
import com.dtoproject.projectdto.shared.ProdutoDTO;
import com.dtoproject.projectdto.view.model.ProdutoRequest;
import com.dtoproject.projectdto.view.model.ProdutoResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
  @Autowired
  private ProdutoService produtoService;


  @GetMapping
  public ResponseEntity<List<ProdutoResponse>> obterTodos(){
    //pegando o produtoDto para covertê-lo 
    List<ProdutoDTO> produtos = produtoService.obterTodos();
    
    //chamando o mapper
    ModelMapper mapper = new ModelMapper();

    //convertendo para produto response
    List<ProdutoResponse> resposta = produtos.stream()
    .map(produtoDto -> mapper.map(produtoDto, ProdutoResponse.class))
    .collect(Collectors.toList());

    //retornar resposta
    return new ResponseEntity<>(resposta, HttpStatus.OK);
   
  }
  @GetMapping("/{id}")
  public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Integer id){
   try{
     //recebendo o produtoDto que está vindo do service
     Optional<ProdutoDTO> dto = produtoService.obterPorId(id);
    
     //convertendo o dto
     ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);
     
     // retornando um responseEntity com status ok
     return new ResponseEntity<>(Optional.of(produto), HttpStatus.OK);

   }catch (Exception e){
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
  }

  @PostMapping
  public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq){
    //inicializando o mapper
    ModelMapper mapper = new ModelMapper();

    //convertendo de produtoReq para produtoDto
    ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);

    //adicionando o produto dto no service
    produtoDto =  produtoService.adicionar(produtoDto);

    //Convertendo de produtoDto para produtoResponse
    ProdutoResponse produtoResponse = mapper.map(produtoDto, ProdutoResponse.class);

    //retornando a resposta com status
    return new ResponseEntity<>(produtoResponse, HttpStatus.CREATED);
  }

 

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletar(@PathVariable Integer id){
    produtoService.deletar(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Integer id, @RequestBody ProdutoRequest produtoReq){
    ModelMapper mapper = new ModelMapper();

    //convertendo produto req em dto
    ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);
    
    //SALVANDO PRODUTO
    produtoDto =  produtoService.atualizar(id, produtoDto);

    //CONVERTENDO PARA RETORNAR
    ProdutoResponse produtoResponse = mapper.map(produtoDto, ProdutoResponse.class);
    
    return new ResponseEntity<>(produtoResponse, HttpStatus.OK);
  }
}
