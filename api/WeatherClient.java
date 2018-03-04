package puciek.customannotation.internalLib.api;

import feign.Feign;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WeatherClient {
    @Value("${api.weather.url}")
    String weatherApiUrl;

    @Bean
    public WeatherApi builder(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logLevel(Logger.Level.FULL)
                .logger(new Logger() {
                    @Override
                    protected void log(String configKey, String format, Object... args) {
                        log.info(configKey + ", " + String.format(format, args));
                    }
                })
                .target(WeatherApi.class, weatherApiUrl);
    }


    public interface WeatherApi {
        @RequestLine("GET /weather?q={city}")
        String weather(@Param("city") String city);
    }
}
