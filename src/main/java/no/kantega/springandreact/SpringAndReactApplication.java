	package no.kantega.springandreact;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import no.kantega.springandreact.controller.SampleController;
import no.kantega.springandreact.repository.*;

@SpringBootApplication
public class SpringAndReactApplication extends SpringBootServletInitializer {
	
	final static Logger logger = Logger.getLogger(SampleController.class);
	
	@Autowired
	CustomerRepository repository;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringAndReactApplication.class);
    }

	public static void main(String args[]) {
		logger.info("main class");
		SpringApplication.run(SpringAndReactApplication.class, args);
	}
	
	/*@Override
	public void run(String... args) throws Exception {

		//logger.info("Student id 10001 -> {}", repository.findById("123"));
		
		logger.info("All users 1 -> {}", repository.findAll());
		
		logger.info("Inserting -> {}", repository.insert(new STG_CCAR_STATISTICS()));

		logger.info("All users 2 -> {}", repository.findAll());

	}*/
	
	

}
