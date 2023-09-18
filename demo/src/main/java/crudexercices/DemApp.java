package crudexercices;

import crudexercices.persistence.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemApp {
    public static void main(String[] args) {
        SpringApplication.run(DemApp.class);
    }

    @Component
    public class Runner implements CommandLineRunner {
        private final CustomerRepository repository;

        public Runner(CustomerRepository repository) {
            this.repository = repository;
        }

        @Override
        public void run(String... args) {
            // work with the repository here
        }
    }
}
