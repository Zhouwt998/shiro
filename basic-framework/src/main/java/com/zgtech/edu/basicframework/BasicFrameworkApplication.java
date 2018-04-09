package com.zgtech.edu.basicframework;

import org.nutz.dao.Dao;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@SpringBootApplication
public class BasicFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicFrameworkApplication.class, args);
    }
}
