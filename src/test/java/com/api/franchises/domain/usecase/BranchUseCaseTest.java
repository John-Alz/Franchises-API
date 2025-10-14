package com.api.franchises.domain.usecase;

import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    BranchPersistencePort branchPersistencePort;

    @Mock
    FranchisePersistencePort franchisePersistencePort;

    BranchUseCase useCase;

    private static final long FRANCHISE_ID = 10L;
    private static final long BRANCH_ID = 5L;
    private static final String NAME = "Sucursal Norte";
    private static final String NEW_NAME = "Sucursal Centro";
    private static final String MSG = "msg-1";
    private static final long PERSISTED_ID = 1L;

    @BeforeEach
    void init() {
        useCase = new BranchUseCase(branchPersistencePort, franchisePersistencePort);
    }

    private Branch sample(Long id, String name, Long franchiseId) {
        return new Branch(id, name, franchiseId);
    }

    @Test
    void shouldSaveBranch_whenFranchiseExist() {
        Branch branch = sample(BRANCH_ID, NAME, FRANCHISE_ID);

        when(franchisePersistencePort.existById(FRANCHISE_ID)).thenReturn(Mono.just(true));
        when(branchPersistencePort.saveBranch(eq(FRANCHISE_ID), any(Branch.class)))
                .thenReturn(Mono.just(new Branch(BRANCH_ID, NAME, FRANCHISE_ID)));

        StepVerifier.create(useCase.saveBranch(FRANCHISE_ID, branch, MSG))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(BRANCH_ID);
                    assertThat(saved.name()).isEqualTo(NAME);
                    assertThat(saved.franchiseId()).isEqualTo(FRANCHISE_ID);
                })
                .verifyComplete();

        ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
        verify(branchPersistencePort).saveBranch(eq(FRANCHISE_ID), captor.capture());
        Branch passed = captor.getValue();
        assertThat(passed.id()).isNull();
        assertThat(passed.name()).isEqualTo(NAME);
        assertThat(passed.franchiseId()).isEqualTo(FRANCHISE_ID);
    }

    @Test
    void shouldError_onSaveBranch_whenFranchiseNotFound() {
        Branch request = sample(111L, NAME, 222L);

        when(franchisePersistencePort.existById(FRANCHISE_ID)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.saveBranch(FRANCHISE_ID, request, MSG))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.FRANCHISE_NOT_FOUND);
                    assertThat(be.getMessage())
                            .contains(TechnicalMessage.FRANCHISE_NOT_FOUND.getMessage());
                })
                .verify();

        verify(franchisePersistencePort).existById(FRANCHISE_ID);
        verify(branchPersistencePort, never()).saveBranch(anyLong(), any());
        verifyNoMoreInteractions(franchisePersistencePort, branchPersistencePort);
    }

    @Test
    void shouldUpdateBranchName_whenFranchiseExists_andRowsUpdated() {
        when(franchisePersistencePort.existById(FRANCHISE_ID)).thenReturn(Mono.just(true));
        when(branchPersistencePort.updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME))
                .thenReturn(Mono.just(1));

        StepVerifier.create(useCase.updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME))
                .verifyComplete();

        verify(franchisePersistencePort).existById(FRANCHISE_ID);
        verify(branchPersistencePort).updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME);
        verifyNoMoreInteractions(franchisePersistencePort, branchPersistencePort);
    }

    @Test
    void shouldError_onUpdateName_whenFranchiseNotFound() {
        when(franchisePersistencePort.existById(FRANCHISE_ID)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.FRANCHISE_NOT_FOUND);
                })
                .verify();

        verify(franchisePersistencePort).existById(FRANCHISE_ID);
        verify(branchPersistencePort, never()).updateNameBranch(anyLong(), anyLong(), anyString());
        verifyNoMoreInteractions(franchisePersistencePort, branchPersistencePort);
    }

    @Test
    void shouldError_onUpdateName_whenRowsIsZero() {
        when(franchisePersistencePort.existById(FRANCHISE_ID)).thenReturn(Mono.just(true));
        when(branchPersistencePort.updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME))
                .thenReturn(Mono.just(0));

        StepVerifier.create(useCase.updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.BRANCH_NOT_IN_FRANCHISE_OR_NOT_FOUND);
                    assertThat(be.getMessage())
                            .contains(TechnicalMessage.BRANCH_NOT_IN_FRANCHISE_OR_NOT_FOUND.getMessage());
                })
                .verify();

        verify(franchisePersistencePort).existById(FRANCHISE_ID);
        verify(branchPersistencePort).updateNameBranch(FRANCHISE_ID, BRANCH_ID, NEW_NAME);
        verifyNoMoreInteractions(franchisePersistencePort, branchPersistencePort);
    }



}