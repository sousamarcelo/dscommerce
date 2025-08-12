package com.devsuperior.dscommerce.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.Order;
import com.devsuperior.dscommerce.entities.OrderItem;
import com.devsuperior.dscommerce.entities.OrderStatus;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Transactional(readOnly = true)	
	public OrderDTO findById(Long id) {
		Optional<Order> result  = repository.findById(id);
		Order order = result.orElseThrow(() -> new ResourceNotFoundException("Recuros não encontrado")); //"orElseThrow()" --> o result que é um tipo Optional já tem um exceção, não necessita de try/catch
		OrderDTO dto = new OrderDTO(order);
		return dto;	
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for(OrderItemDTO itemDTO : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}

}
