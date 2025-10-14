package com.api.franchises.domain.usecase;

import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportingUseCaseTest {

    @Mock
    ReportingPersistencePort reportingPersistencePort;

    @Mock
    FranchisePersistencePort franchisePersistencePort;

    ReportingUseCase useCase;

    private static final Long BRANCH_ID = 1L;
    private static final String BRACH_NAME = "Pollos hermanos norte";
    private static final Long PRODUCT_ID = 2L;
    private static final String PRODUCT_NAME = "Pollo asado";
    private static final Integer STOCK = 200;

    @BeforeEach
    void init() {
        useCase = new ReportingUseCase(reportingPersistencePort, franchisePersistencePort);
    }


    @Test
    void test1() {

        TopProductPerBranch item1 = new TopProductPerBranch(BRANCH_ID, BRACH_NAME, PRODUCT_ID, PRODUCT_NAME, STOCK);
        TopProductPerBranch item2 = mock(TopProductPerBranch.class);

        when(franchisePersistencePort.existById(1L)).thenReturn(Mono.just(true));
        when(reportingPersistencePort.topProductPerBranch(1L)).thenReturn(Flux.just(item1, item2));

        Flux<TopProductPerBranch> result = useCase.topProductPerBranch(1L);

        StepVerifier.create(result)
                .expectNext(item1)
                .expectNext(item2)
                .verifyComplete();

        verify(franchisePersistencePort).existById(1L);
        verify(reportingPersistencePort).topProductPerBranch(1L);
        verifyNoMoreInteractions(franchisePersistencePort, reportingPersistencePort);
    }

    @Test
    void test2() {
        when(franchisePersistencePort.existById(1L)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.topProductPerBranch(1L))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage()).isEqualTo(TechnicalMessage.FRANCHISE_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.FRANCHISE_NOT_FOUND.getMessage());
                })
                .verify();

        verify(franchisePersistencePort).existById(1L);
        verify(reportingPersistencePort, never()).topProductPerBranch(anyLong());
        verifyNoMoreInteractions(franchisePersistencePort, reportingPersistencePort);

    }

}