package com.example.Ordenador.Controller;

import com.example.Ordenador.Entity.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {

            ResponseEntity<Laptop[]> response  =
                    testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            //assertEquals(200, response.getStatusCodeValue());

            List<Laptop> laptops = Arrays.asList(response.getBody());

        for (Laptop laptop: laptops) {
            System.out.println(laptop.toString());
        }
            System.out.println(laptops.size());


    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response  =
                testRestTemplate.getForEntity("/api/laptop/1", Laptop.class);

        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "nombre": "Acer creado desde Spring Test"
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

       // assertEquals(4L, result.getId());
        assertEquals("Acer creado desde Spring Test", result.getNombre());
        findAll();
    }

    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
               {
               "id": 1,
               "nombre": "Lenovo modificado desde Spring Test"
               }
               """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT, request, Laptop.class);
        Laptop resultado = response.getBody();
        assertEquals(1l, resultado.getId());
        assertEquals("Lenovo modificado desde Spring Test", resultado.getNombre());
        findAll();
    }


    @Test
    void delete() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/455", Laptop.class);
        Laptop laptop = response.getBody();
        testRestTemplate.delete("/api/laptops/455",laptop,"/api/laptops/{id}");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        findAll();
    }


    @Test
    void deleteAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        if(response.getBody() != null){
            testRestTemplate.delete("/api/laptops");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            System.out.println("OKKKKK");
        }else{
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            System.out.println("NO OKK");
        }
    }


}