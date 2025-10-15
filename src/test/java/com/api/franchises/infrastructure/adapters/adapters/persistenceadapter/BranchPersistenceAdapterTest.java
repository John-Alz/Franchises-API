package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.model.Franchise;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.BranchEntity;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.FranchiseEntity;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.BranchEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.BranchRepository;
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
class BranchPersistenceAdapterTest {

    @Mock
    BranchRepository repository;

    @Mock
    BranchEntityMapper mapper;

    BranchPersistenceAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new BranchPersistenceAdapter(repository, mapper);
    }

    Branch model(Long id, String name, Long franchiseId) {
        return new Branch(id, name, franchiseId);
    }
    BranchEntity entity(Long id,String name, Long franchiseId) {
        return new BranchEntity(id, name, franchiseId);
    }

    @Test
    void test1() {
        Long FRANCHISE_ID = 10L;
        Branch input = model(null, "Sucursal Norte", FRANCHISE_ID);
        BranchEntity toSave = entity(null, "Sucursal Norte", FRANCHISE_ID);
        BranchEntity saved = entity(1L, "Sucursal Norte", FRANCHISE_ID);
        Branch expected = model(1L, "Sucursal Norte", FRANCHISE_ID);

        when(mapper.toEntity(input)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(Mono.just(saved));
        when(mapper.toModel(saved)).thenReturn(expected);

        StepVerifier.create(adapter.saveBranch(FRANCHISE_ID, input))
                .expectNext(expected)
                .verifyComplete();

        verify(mapper).toEntity(input);
        verify(repository).save(toSave);
        verify(mapper).toModel(saved);
        verifyNoMoreInteractions(mapper, repository);

    }

    @Test
    void existById_shouldDelegateToRepository() {
        when(repository.existsById(99L)).thenReturn(Mono.just(true));

        StepVerifier.create(adapter.existById(99L))
                .expectNext(true)
                .verifyComplete();

        verify(repository).existsById(99L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }


    @Test
    void updateNameBranch_shouldDelegateToRepository() {
        when(repository.updateNameBranch(10L, 5L, "Nuevo"))
                .thenReturn(Mono.just(1));

        StepVerifier.create(adapter.updateNameBranch(10L, 5L, "Nuevo"))
                .expectNext(1)
                .verifyComplete();

        verify(repository).updateNameBranch(10L, 5L, "Nuevo");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }
}
