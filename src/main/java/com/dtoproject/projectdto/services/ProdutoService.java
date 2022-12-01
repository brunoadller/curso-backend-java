package com.dtoproject.projectdto.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtoproject.projectdto.model.Produto;
import com.dtoproject.projectdto.model.exception.ResourceNotFoundException;
import com.dtoproject.projectdto.repository.ProdutoRepository;
import com.dtoproject.projectdto.shared.ProdutoDTO;

@Service
public class ProdutoService {
  
  @Autowired
  private ProdutoRepository produtoRepository;

  public List<ProdutoDTO> obterTodos(){
    //retorna uma lista de produtos model
    List<Produto> produtos = produtoRepository.findAll();

    List<ProdutoDTO>  produtoDto = produtos.stream()
    .map(produto -> new ModelMapper()
    .map(produtos, ProdutoDTO.class))//converte em produto dto
    .collect(Collectors.toList());

    return produtoDto; 
    
  }

  public Optional<ProdutoDTO> obterPorId(Integer id){

    //obtendo optional de produtos pelo id
    Optional<Produto> produto =  produtoRepository.findById(id);

    //se não encontrar lança exception
    if(produto.isEmpty()){
      throw new ResourceNotFoundException("Produto com id: "+id+" não encontrado");
    }

    //Convertendo meu optional de produto em um produtoDTO
    ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
    
    //Criando e retornando um optional de produtoDTO
    return Optional.of(dto);
  }

  public ProdutoDTO adicionar(ProdutoDTO produtoDto){
    //Removendo o id para conseguir fazer o cadastro
    produtoDto.setId(null);

    //Criar um objeto de mapeamento
    ModelMapper mapper = new ModelMapper();

    //converter o nosso produtoDTO em um produto
    Produto produto = mapper.map(produtoDto, Produto.class);

    //salvar o produto no banco
    produto = produtoRepository.save(produto);

    produtoDto.setId(produto.getId());

    //Retornar o produtoDTO atualizado

    return produtoDto;



  }
  

  public void deletar(Integer id){
    //verificar se o produto existe
    Optional<Produto> produto = produtoRepository.findById(id);
    
    //se não existir lança uma exception
    if(produto.isEmpty()){
      throw new ResourceNotFoundException("Não foi possível deletar o produto com o id: "+id+" - produto não existe");
    }

    // deleta o produto pelo id
    produtoRepository.deleteById(id);
  }

  public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
    //Passar o id para produtoDto
    produtoDto.setId(id);

    // criar um objeto de mapeamento
    ModelMapper mapper = new ModelMapper();

    //coverter o ProdutoDTO em um Produto
    Produto produto = mapper.map(produtoDto, Produto.class);

    //Atualizar o produto no Banco de dados
    produtoRepository.save(produto);

    //Retornar o produtoDto atualizado
    return produtoDto;
  }
}
