package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.dto.ProductMinDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.DataBaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repostory;
	
	@Transactional(readOnly = true)	
	public ProductDTO findById(Long id) {
		Optional<Product> result  = repostory.findById(id);
		Product product = result.orElseThrow(() -> new ResourceNotFoundException("Recuros não encontrado")); //"orElseThrow()" --> o result que é um tipo Optional já tem um exceção, não necessita de try/catch
		ProductDTO dto = new ProductDTO(product);
		return dto;	
	}
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {  //Pageable--> paginação
		Page<Product> result  = repostory.searchByName(name, pageable); //		
		return 	result.map(x -> new ProductMinDTO(x)); // para o Page não precisa de stream, chamar o map diretamente	
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {  		
		Product entity = new Product();
		copyDTOToEntoty(dto, entity);		
		entity = repostory.save(entity); //salva a variavel entity no banco utiliando o repository e ao mesmo tempo retorna		
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {	
		try {
			Product entity = repostory.getReferenceById(id); //getReferenceById(id) --> não busca no banco, trabalha com os dados monitorados
			copyDTOToEntoty(dto, entity);		
			entity = repostory.save(entity); //salva a variavel entity no banco utiliando o repository e ao mesmo tempo retorna		
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recuso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS) //só executa se o metodo estiver no contexto de outra transação
	public void delete(Long id) {
		if(!repostory.existsById(id)) {
			throw new ResourceNotFoundException("Recuso não encontrado");
		}
		
		try {
			repostory.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			 throw new DataBaseException("Falha de integridade referencial");
		}
	}

	private void copyDTOToEntoty(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());		
	}	
}
