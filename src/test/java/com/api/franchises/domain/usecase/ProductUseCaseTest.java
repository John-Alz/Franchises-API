package com.api.franchises.domain.usecase;

import com.api.franchises.domain.enums.TechnicalMessage;
import com.api.franchises.domain.exceptions.BusinessException;
import com.api.franchises.domain.model.Product;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.domain.spi.ProductPersistencePort;
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
class ProductUseCaseTest {

    @Mock
    ProductPersistencePort productPersistencePort;

    @Mock
    BranchPersistencePort branchPersistencePort;

    ProductUseCase useCase;

    private static final long BRANCH_ID = 10L;
    private static final long PRODUCT_ID = 7L;
    private static final String NAME = "Salsa Picante";
    private static final int STOCK = 5;
    private static final String MSG = "msg-1";

    @BeforeEach
    void init() {
        useCase = new ProductUseCase(productPersistencePort, branchPersistencePort);
    }


    @Test
    void shouldSaveProduct_whenBranchExists() {
        Product request = new Product(999L, NAME, STOCK, 123L);

        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.saveProduct(any(Product.class)))
                .thenAnswer(inv -> {
                    Product p = inv.getArgument(0);
                    return Mono.just(new Product(1L, p.name(), p.stock(), p.branchId()));
                });

        StepVerifier.create(useCase.saveProduct(BRANCH_ID, request, MSG))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(1L);
                    assertThat(saved.name()).isEqualTo(NAME);
                    assertThat(saved.stock()).isEqualTo(STOCK);
                    assertThat(saved.branchId()).isEqualTo(BRANCH_ID);
                })
                .verifyComplete();

        verify(branchPersistencePort).existById(BRANCH_ID);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productPersistencePort).saveProduct(captor.capture());
        Product built = captor.getValue();
        assertThat(built.id()).isNull();
        assertThat(built.name()).isEqualTo(NAME);
        assertThat(built.stock()).isEqualTo(STOCK);
        assertThat(built.branchId()).isEqualTo(BRANCH_ID);

        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onSaveProduct_whenBranchNotFound() {
        Product request = new Product(null, NAME, STOCK, BRANCH_ID);
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.saveProduct(BRANCH_ID, request, MSG))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    assertThat(((BusinessException) ex).getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.BRANCH_NOT_FOUND);
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verifyNoInteractions(productPersistencePort);
    }

    @Test
    void shouldDeleteProduct_whenBranchExists_andRowsUpdated() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.deleteProduct(BRANCH_ID, PRODUCT_ID)).thenReturn(Mono.just(1));

        StepVerifier.create(useCase.deleteProduct(BRANCH_ID, PRODUCT_ID, MSG))
                .verifyComplete();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).deleteProduct(BRANCH_ID, PRODUCT_ID);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);

    }

    @Test
    void shouldError_onDeleteProduct_whenBranchNotFound() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.deleteProduct(BRANCH_ID, PRODUCT_ID, MSG))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage()).isEqualTo(TechnicalMessage.BRANCH_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.BRANCH_NOT_FOUND.getMessage());
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onDeleteProduct_whenRowsIsZero() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.deleteProduct(BRANCH_ID, PRODUCT_ID)).thenReturn(Mono.just(0));

        StepVerifier.create(useCase.deleteProduct(BRANCH_ID, PRODUCT_ID, MSG))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    assertThat(((BusinessException) ex).getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND);
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).deleteProduct(BRANCH_ID, PRODUCT_ID);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldUpdateStock_whenBranchExists_andRowsUpdated() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK)).thenReturn(Mono.just(1));

        StepVerifier.create(useCase.updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK))
                .verifyComplete();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onUpdateStock_whenNegativeStock() {
        StepVerifier.create(useCase.updateStockProduct(BRANCH_ID, PRODUCT_ID, -1))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage()).isEqualTo(TechnicalMessage.NEGATIVE_STOCK);
                    assertThat(be.getMessage()).contains(TechnicalMessage.NEGATIVE_STOCK.getMessage());
                })
                .verify();

        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onUpdateStock_whenRowsIsZero() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK)).thenReturn(Mono.just(0));

        StepVerifier.create(useCase.updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND.getMessage());
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).updateStockProduct(BRANCH_ID, PRODUCT_ID, STOCK);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldUpdateName_whenBranchExists_andRowsUpdated() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME)).thenReturn(Mono.just(1));

        StepVerifier.create(useCase.updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME))
                .verifyComplete();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onUpdateName_whenBranchNotFound() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(false));

        StepVerifier.create(useCase.updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage()).isEqualTo(TechnicalMessage.BRANCH_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.BRANCH_NOT_FOUND.getMessage());
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort, never()).updateNameProduct(anyLong(), anyLong(), anyString());
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

    @Test
    void shouldError_onUpdateName_whenRowsIsZero() {
        when(branchPersistencePort.existById(BRANCH_ID)).thenReturn(Mono.just(true));
        when(productPersistencePort.updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME)).thenReturn(Mono.just(0));

        StepVerifier.create(useCase.updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME))
                .expectErrorSatisfies(ex -> {
                    assertThat(ex).isInstanceOf(BusinessException.class);
                    BusinessException be = (BusinessException) ex;
                    assertThat(be.getTechnicalMessage())
                            .isEqualTo(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND);
                    assertThat(be.getMessage()).contains(TechnicalMessage.PRODUCT_NOT_IN_BRANCH_OR_NOT_FOUND.getMessage());
                })
                .verify();

        verify(branchPersistencePort).existById(BRANCH_ID);
        verify(productPersistencePort).updateNameProduct(BRANCH_ID, PRODUCT_ID, NAME);
        verifyNoMoreInteractions(branchPersistencePort, productPersistencePort);
    }

}