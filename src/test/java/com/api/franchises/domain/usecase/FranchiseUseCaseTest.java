package com.api.franchises.domain.usecase;

import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.as;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {

    @Mock
    FranchisePersistencePort franchisePersistencePort;

    FranchiseUseCase useCase;

    private static final Long ID = 1L;
    private static final String NAME = "Pollos hermanos";
    private static final String MSG = "MSG-1";

    @BeforeEach
    void init() {
        useCase = new FranchiseUseCase(franchisePersistencePort);
    }

    private Franchise sample(Long id, String name) {
        return new Franchise(id, name);
    }

    @Test
    void shouldSaveFranchise_whenNameDoesNotExist() {
        Franchise franchise = sample(1L, NAME);

        when(franchisePersistencePort.existByName(NAME)).thenReturn(Mono.just(false));

        when(franchisePersistencePort.saveFranchise(franchise)).thenReturn(Mono.just(franchise));

        Mono<Franchise> result = useCase.saveFranchise(franchise, MSG);

        StepVerifier.create(result)
                .expectNextMatches(fr -> fr.equals(franchise))
                .verifyComplete();

        verify(franchisePersistencePort).existByName(NAME);
        verify(franchisePersistencePort).saveFranchise(franchise);
        verifyNoMoreInteractions(franchisePersistencePort);

    }

    @Test
    void shouldError_whenFranchiseAlreadyExists() {
        Franchise franchise = sample(1L, NAME);

        when(franchisePersistencePort.existByName(NAME)).thenReturn(Mono.just(true));

        Mono<Franchise> result = useCase.saveFranchise(franchise, MSG);

        StepVerifier.create(result)
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.FRANCHISE_ALREADY_EXISTS);
                    assertThat(be.getMessage()).contains(TechnicalMessage.FRANCHISE_ALREADY_EXISTS.getMessage());
                })
                .verify();

        verify(franchisePersistencePort).existByName(NAME);
        verify(franchisePersistencePort, never()).saveFranchise(any());
        verifyNoMoreInteractions(franchisePersistencePort);

    }

    @Test
    void shouldUpdateName_whenRowsGreaterThanZero_thenComplete() {

        when(franchisePersistencePort.updateNameFranchise(ID, NAME)).thenReturn(Mono.just(1));

        StepVerifier.create(useCase.updateNameFranchise(ID, NAME))
                .verifyComplete();

        verify(franchisePersistencePort).updateNameFranchise(ID, NAME);
        verifyNoMoreInteractions(franchisePersistencePort);

    }

    @Test
    void shouldError_whenRowsIsZero_thenFranchiseNotFound() {
        when(franchisePersistencePort.updateNameFranchise(ID, NAME)).thenReturn(Mono.just(0));

        StepVerifier.create(useCase.updateNameFranchise(ID, NAME))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.FRANCHISE_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.FRANCHISE_NOT_FOUND.getMessage());
                })
                .verify();

        verify(franchisePersistencePort).updateNameFranchise(ID, NAME);
        verifyNoMoreInteractions(franchisePersistencePort);

    }

}