package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	
	@Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
	@NotBlank(message = "Campo requerido") //annotation da blibioteca BinValidation, não aceita espaços vazios no nome
	private String name;
	
	@Size(min = 10, message = "Descrição precisa ter no minimo 10 caracteres")
	@NotBlank(message = "Campo requerido") //annotation da blibioteca BinValidation, não aceita espaços vazios no nome
	private String description;
	
	@Positive(message = "O preço deve ser positivo")
	private Double price;
	
	private String imgUrl;
	
	public ProductDTO() {
	}
	
	//como só existe os metodos gets é interessante manter somente o contrutor com argumentos
	public ProductDTO(Product product) {
		id = product.getId();
		name = product.getName();
		description = product.getDescription();
		price = product.getPrice();
		imgUrl = product.getImgUrl();
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}
}
