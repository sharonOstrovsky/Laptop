package com.example.Ordenador.Service;

import com.example.Ordenador.Entity.Laptop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaptopServiceTest {

    @Test
    void calcularPrecio() {

        Laptop laptop = new Laptop(null,"Lenovo");
        LaptopService laptopService = new LaptopService();

        double precio = laptopService.calcularPrecio(laptop);
        System.out.println(precio);

        assertTrue(precio> 0);
        assertEquals(5000.0,precio);

    }
}