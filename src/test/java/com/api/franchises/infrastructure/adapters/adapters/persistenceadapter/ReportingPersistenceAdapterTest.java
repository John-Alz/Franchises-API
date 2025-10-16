package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.dto.TopProductPerBranchRow;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ReportingDtoMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ReportingRepository;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.FetchSpec;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportingPersistenceAdapterTest {

    @Mock
    ReportingRepository repository;

    @Mock
    ReportingDtoMapper mapper;

    ReportingPersistencePort adapter;

    @BeforeEach
    void setUp() {
        adapter = new ReportingPersistenceAdapter(repository, mapper);
    }

    @Test
    void shouldReturnMappedDomainResults() {
        Long franchiseId = 10L;

        TopProductPerBranchRow row1 = new TopProductPerBranchRow(1L, "Sucursal Centro", 100L, "Mouse", 300);
        TopProductPerBranchRow row2 = new TopProductPerBranchRow(2L, "Sucursal Norte", 200L, "Teclado", 500);

        when(repository.findTopProductPerBranchByFranchiseId(franchiseId))
                .thenReturn(Flux.just(row1, row2));

        TopProductPerBranch model1 = new TopProductPerBranch(1L, "Sucursal Centro", 100L, "Mouse", 300);
        TopProductPerBranch model2 = new TopProductPerBranch(2L, "Sucursal Norte", 200L, "Teclado", 500);

        when(mapper.toModel(row1)).thenReturn(model1);
        when(mapper.toModel(row2)).thenReturn(model2);

        StepVerifier.create(adapter.topProductPerBranch(franchiseId))
                .expectNext(model1, model2)
                .verifyComplete();

        InOrder inOrder = inOrder(repository, mapper);
        inOrder.verify(repository).findTopProductPerBranchByFranchiseId(franchiseId);
        inOrder.verify(mapper).toModel(row1);
        inOrder.verify(mapper).toModel(row2);
        inOrder.verifyNoMoreInteractions();
    }

}