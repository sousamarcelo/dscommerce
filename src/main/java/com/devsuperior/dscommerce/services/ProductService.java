package com.devsuperior.dscommerce.services;

import java.util.List;
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
}
