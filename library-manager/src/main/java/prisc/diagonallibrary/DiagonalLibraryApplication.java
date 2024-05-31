package prisc.diagonallibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:credential.properties")
@SpringBootApplication
public class DiagonalLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagonalLibraryApplication.class, args);
	}

}
