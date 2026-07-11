package com.rzodeczko.infrastructure.configuration;


import com.rzodeczko.application.port.out.SagaCommandPort;
import com.rzodeczko.application.port.out.SagaInstanceRepository;
import com.rzodeczko.application.service.SagaOrchestratorImpl;
import com.rzodeczko.application.service.SagaQueryServiceImpl;
import com.rzodeczko.infrastructure.tx.TransactionalSagaOrchestrator;
import com.rzodeczko.infrastructure.tx.TransactionalSagaQueryService;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30s")
public class BeanConfiguration {

    @Bean
    public LockProvider lockProvider(JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration
                .builder()
                .withJdbcTemplate(jdbcTemplate)
                .usingDbTime()
                .build());
    }

    @Bean
    public SagaOrchestratorImpl sagaOrchestratorImpl(
            SagaInstanceRepository sagaInstanceRepository,
            SagaCommandPort sagaCommandPort
    ) {
        return new SagaOrchestratorImpl(sagaInstanceRepository, sagaCommandPort);
    }

    @Bean
    public SagaQueryServiceImpl sagaQueryServiceImpl(SagaInstanceRepository sagaInstanceRepository) {
        return new SagaQueryServiceImpl(sagaInstanceRepository);
    }

    @Bean("transactionalSagaOrchestrator")
    public TransactionalSagaOrchestrator transactionalSagaOrchestrator(SagaOrchestratorImpl sagaOrchestratorImpl) {
        return new TransactionalSagaOrchestrator(sagaOrchestratorImpl);
    }

    @Bean("transactionalSagaQueryService")
    public TransactionalSagaQueryService transactionalSagaQueryService(SagaQueryServiceImpl sagaQueryServiceImpl) {
        return new TransactionalSagaQueryService(sagaQueryServiceImpl);
    }
}
