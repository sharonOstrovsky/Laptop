package com.example.Ordenador.Repository;

import com.example.Ordenador.Entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaptopRepository extends JpaRepository<Laptop,Long> {
}
