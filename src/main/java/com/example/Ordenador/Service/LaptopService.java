package com.example.Ordenador.Service;

import com.example.Ordenador.Entity.Laptop;

public class LaptopService {

    public double calcularPrecio(Laptop laptop){
        int tam = laptop.getNombre().length();
        double precio = 3000;
        if(tam > 3){
            precio += 2000;
        }else if(tam <= 3){
            precio += 10000;
        }
        return precio;
    }
}
