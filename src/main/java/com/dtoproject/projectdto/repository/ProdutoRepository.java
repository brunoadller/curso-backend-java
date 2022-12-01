package com.dtoproject.projectdto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dtoproject.projectdto.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  
}
