package prisc.librarymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:credential.properties")
@SpringBootApplication
public class LibraryManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagerApplication.class, args);

    }

}
