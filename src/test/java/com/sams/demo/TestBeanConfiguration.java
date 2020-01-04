package com.sams.demo;

import com.sams.demo.repository.QuestionRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestBeanConfiguration {

    @Bean
    @Primary
    public QuestionRepository mockQuestionRepository() {
        return mock(QuestionRepository.class);
    }
}