package com.devsuperior.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	
	@Transactional(readOnly = true)	
	public OrderDTO findById(Long id) {
		Optional<Order> result  = repository.findById(id);
		Order order = result.orElseThrow(() -> new ResourceNotFoundException("Recuros não encontrado")); //"orElseThrow()" --> o result que é um tipo Optional já tem um exceção, não necessita de try/catch
		OrderDTO dto = new OrderDTO(order);
		return dto;	
	}

}
