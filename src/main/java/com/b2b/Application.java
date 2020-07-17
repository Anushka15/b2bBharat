package com.b2b;

import com.b2b.model.Type;
import com.b2b.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        run(Application.class, args);
    }

}
