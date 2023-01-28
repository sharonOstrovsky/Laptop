package com.example.Ordenador;

import com.example.Ordenador.Entity.Laptop;
import com.example.Ordenador.Repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrdenadorApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrdenadorApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop(null, "Lenovo");
		Laptop laptop2 = new Laptop(null, "Dell");
		Laptop laptop3 = new Laptop(null,"MacBook");
		Laptop laptop4 = new Laptop(null, "HP");

		repository.save(laptop1);
		repository.save(laptop2);
		repository.save(laptop3);
		repository.save(laptop4);

	}

}
