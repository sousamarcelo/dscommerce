package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repostory;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> result  = repostory.findById(id);
		Product product = result.get();
		ProductDTO dto = new ProductDTO(product);
		return dto;		
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {  //Pageable--> paginação
		Page<Product> result  = repostory.findAll(pageable); //		
		return 	result.map(x -> new ProductDTO(x)); // para o Page não precisa de stream, chamar o map diretamente	
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
		Product entity = repostory.getReferenceById(id); //getReferenceById(id) --> não busca no banco, trabalha com os dados monitorados
		copyDTOToEntoty(dto, entity);		
		entity = repostory.save(entity); //salva a variavel entity no banco utiliando o repository e ao mesmo tempo retorna		
		return new ProductDTO(entity);
	}

	private void copyDTOToEntoty(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());		
	}
}
