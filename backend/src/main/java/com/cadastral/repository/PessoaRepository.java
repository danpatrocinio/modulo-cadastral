package com.cadastral.repository;

import com.cadastral.entidy.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pessoas", path = "pessoas")
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {
}
