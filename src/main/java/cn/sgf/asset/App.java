package cn.sgf.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class App {
	
    public static void main(String[] args ){
    	SpringApplication springApplication = new SpringApplication(App.class);
    	springApplication.addListeners(new AppStartup());
    	springApplication.run(args);
    }
    
}
