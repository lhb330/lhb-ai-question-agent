package com.lhb.questionweb.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lhb.utils.DateUtil;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD_HH_MM_SS);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtil.HH_MM_SS);

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            builder.serializers(
                    new LocalDateTimeSerializer(DATE_TIME_FORMATTER),
                    new LocalDateSerializer(DATE_FORMATTER),
                    new LocalTimeSerializer(TIME_FORMATTER)
            );
            builder.deserializers(
                    new LocalDateTimeDeserializer(DATE_TIME_FORMATTER),
                    new LocalDateDeserializer(DATE_FORMATTER),
                    new LocalTimeDeserializer(TIME_FORMATTER)
            );
        };
    }
}
