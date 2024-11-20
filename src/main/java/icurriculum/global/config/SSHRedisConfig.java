package icurriculum.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories(basePackages = "icurriculum.global.redis")
public class SSHRedisConfig {

    private final SSHConfig initializer;

    @Value("${server}")
    private String isServer;

    @Value("${cloud.aws.ec2.redis_endpoint}")
    private String redisEndpoint;

    @Value("${cloud.aws.ec2.redis_port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String host = redisEndpoint;
        int port = redisPort;

        // SSH 터널을 통해 Redis에 연결해야 할 경우
        if (isServer.equals("false")) {
            Integer forwardedPort = initializer.buildSshConnection(redisEndpoint, redisPort);
            host = "localhost";
            port = forwardedPort;
        }

        log.info("Redis connection through SSH: host={}, port={}", host, port);

        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}