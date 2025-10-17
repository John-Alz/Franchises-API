package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.FranchiseEntity;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.FranchiseEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FranchisePersistenceAdapterTest {

    @Mock
    FranchiseRepository repository;
    @Mock
    FranchiseEntityMapper mapper;

    FranchisePersistenceAdapter adapter;

    @BeforeEach
    void init() { adapter = new FranchisePersistenceAdapter(repository, mapper); }

    Franchise model(String name) {
        return new Franchise(1L, name);
    }
    FranchiseEntity entity(String name) {
        return new FranchiseEntity(1L, name);
    }

    @Test
    void saveFranchise_shouldMapToEntity_callRepo_andMapBackToModel() {
        Franchise input = new Franchise(null, "Acme");
        FranchiseEntity toSave = new FranchiseEntity(null, "Acme");
        FranchiseEntity savedEntity = new FranchiseEntity(10L, "Acme");
        Franchise expected = new Franchise(10L, "Acme");

        when(mapper.toEntity(input)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(Mono.just(savedEntity));
        when(mapper.toModel(savedEntity)).thenReturn(expected);

        StepVerifier.create(adapter.saveFranchise(input))
                .expectNext(expected)
                .verifyComplete();

        verify(mapper).toEntity(input);
        verify(repository).save(toSave);
        verify(mapper).toModel(savedEntity);
        verifyNoMoreInteractions(mapper, repository);
    }

    @Test
    void existByName_shouldDelegateToRepository() {
        when(repository.existsByName("Acme")).thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existByName("Acme"))
                .expectNext(true)
                .verifyComplete();

        verify(repository).existsByName("Acme");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void existById_shouldDelegateToRepository() {
        when(repository.existsById(99L)).thenReturn(Mono.just(false));

        StepVerifier.create(adapter.existById(99L))
                .expectNext(false)
                .verifyComplete();

        verify(repository).existsById(99L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void updateNameFranchise_shouldDelegateToRepository() {
        when(repository.updateNameFranchise(5L, "New")).thenReturn(Mono.just(1));

        StepVerifier.create(adapter.updateNameFranchise(5L, "New"))
                .expectNext(1)
                .verifyComplete();

        verify(repository).updateNameFranchise(5L, "New");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

}