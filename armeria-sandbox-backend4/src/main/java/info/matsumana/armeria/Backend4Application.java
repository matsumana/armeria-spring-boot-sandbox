package info.matsumana.armeria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Backend4Application {

    private static final Logger logger = LoggerFactory.getLogger(Backend4Application.class);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Start shutting down");
        }));

        SpringApplication.run(Backend4Application.class, args);
    }
}
