package icurriculum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@EnableJpaAuditing
public class IcurriculumApplication {

    public static void main(String[] args) {
        SpringApplication.run(IcurriculumApplication.class, args);
    }

}
