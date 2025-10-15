package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Product;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.ProductEntity;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ProductEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPersistenceAdapterTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductEntityMapper productEntityMapper;

    ProductPersistenceAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new ProductPersistenceAdapter(productRepository, productEntityMapper);
    }


    private Product model(Long id, String name, int stock, Long branchId) {
        return new Product(id, name, stock, branchId); // si no es record, usa getters/setters
    }
    private ProductEntity entity(Long id, String name, int stock, Long branchId) {
        return new ProductEntity(id, name, stock, branchId);
    }


    @Test
    void saveProduct_shouldMapToEntity_callRepo_andMapBackToModel() {
        Product input = model(null, "Salsa", 5, 10L);
        ProductEntity toSave = entity(null, "Salsa", 5, 10L);
        ProductEntity savedEntity = entity(1L, "Salsa", 5, 10L);
        Product expected = model(1L, "Salsa", 5, 10L);

        when(productEntityMapper.toEntity(input)).thenReturn(toSave);
        when(productRepository.save(toSave)).thenReturn(Mono.just(savedEntity));
        when(productEntityMapper.toModel(savedEntity)).thenReturn(expected);

        StepVerifier.create(adapter.saveProduct(input))
                .expectNext(expected)
                .verifyComplete();

        verify(productEntityMapper).toEntity(input);
        verify(productRepository).save(toSave);
        verify(productEntityMapper).toModel(savedEntity);
        verifyNoMoreInteractions(productEntityMapper, productRepository);
    }

    @Test
    void saveProduct_shouldPropagateError_fromRepository() {
        Product input = model(null, "Salsa", 5, 10L);
        ProductEntity toSave = entity(null, "Salsa", 5, 10L);

        when(productEntityMapper.toEntity(input)).thenReturn(toSave);
        when(productRepository.save(toSave)).thenReturn(Mono.error(new IllegalStateException("db")));

        StepVerifier.create(adapter.saveProduct(input))
                .expectErrorMatches(ex -> ex instanceof IllegalStateException && ex.getMessage().contains("db"))
                .verify();

        verify(productEntityMapper).toEntity(input);
        verify(productRepository).save(toSave);
        verifyNoMoreInteractions(productEntityMapper, productRepository);
    }


    @Test
    void deleteProduct_shouldDelegateToRepository_andEmitCount() {
        long branchId = 10L, productId = 7L;

        when(productRepository.deleteByIdAndBranchId(productId, branchId))
                .thenReturn(Mono.just(1));

        StepVerifier.create(adapter.deleteProduct(branchId, productId))
                .expectNext(1)
                .verifyComplete();

        verify(productRepository).deleteByIdAndBranchId(productId, branchId);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productEntityMapper);
    }

    @Test
    void deleteProduct_shouldPropagateError_fromRepository() {
        long branchId = 10L, productId = 7L;

        when(productRepository.deleteByIdAndBranchId(productId, branchId))
                .thenReturn(Mono.error(new RuntimeException("constraint")));

        StepVerifier.create(adapter.deleteProduct(branchId, productId))
                .expectErrorMatches(ex -> ex instanceof RuntimeException && ex.getMessage().contains("constraint"))
                .verify();

        verify(productRepository).deleteByIdAndBranchId(productId, branchId);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productEntityMapper);
    }

    @Test
    void updateStockProduct_shouldDelegateToRepository() {
        long branchId = 10L, productId = 7L; int newStock = 20;

        when(productRepository.updateStockInBranch(branchId, productId, newStock))
                .thenReturn(Mono.just(1));

        StepVerifier.create(adapter.updateStockProduct(branchId, productId, newStock))
                .expectNext(1)
                .verifyComplete();

        verify(productRepository).updateStockInBranch(branchId, productId, newStock);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productEntityMapper);
    }



    @Test
    void updateNameProduct_shouldDelegateToRepository() {
        long branchId = 10L, productId = 7L; String name = "Nuevo";

        when(productRepository.updateNameInBranch(branchId, productId, name))
                .thenReturn(Mono.just(1));

        StepVerifier.create(adapter.updateNameProduct(branchId, productId, name))
                .expectNext(1)
                .verifyComplete();

        verify(productRepository).updateNameInBranch(branchId, productId, name);
        verifyNoMoreInteractions(productRepository);
        verifyNoInteractions(productEntityMapper);
    }


}