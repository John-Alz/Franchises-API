package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.TopProductPerBranch;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
    DatabaseClient db;
    @Mock
    DatabaseClient.GenericExecuteSpec generic;
    @Mock
    FetchSpec<TopProductPerBranch> fetch;
    ReportingPersistenceAdapter adapter;

    @BeforeEach
    void init() {
        adapter = new ReportingPersistenceAdapter(db);
    }

    @Test
    void topProductPerBranch_shouldBindAndMap_andReturnItems() {
        long franchiseId = 10L;

        AtomicReference<BiFunction<Row, RowMetadata, TopProductPerBranch>> mapperRef = new AtomicReference<>();

        when(db.sql(anyString())).thenReturn(generic);
        when(generic.bind(eq("franchiseId"), eq(franchiseId))).thenReturn(generic);
        when(generic.map(ArgumentMatchers.<BiFunction<Row, RowMetadata, TopProductPerBranch>>any()))
                .thenAnswer(inv -> {
                    mapperRef.set(inv.getArgument(0));
                    return fetch;
                });

        Row row1 = mock(Row.class);
        Row row2 = mock(Row.class);
        RowMetadata meta = mock(RowMetadata.class);

        when(row1.get("branch_id", Long.class)).thenReturn(1L);
        when(row1.get("branch_name", String.class)).thenReturn("Norte");
        when(row1.get("product_id", Long.class)).thenReturn(7L);
        when(row1.get("product_name", String.class)).thenReturn("Salsa");
        when(row1.get("stock", Integer.class)).thenReturn(20);

        when(row2.get("branch_id", Long.class)).thenReturn(2L);
        when(row2.get("branch_name", String.class)).thenReturn("Sur");
        when(row2.get("product_id", Long.class)).thenReturn(5L);
        when(row2.get("product_name", String.class)).thenReturn("Papas");
        when(row2.get("stock", Integer.class)).thenReturn(15);

        when(fetch.all()).thenAnswer(inv -> {
            TopProductPerBranch a = mapperRef.get().apply(row1, meta);
            TopProductPerBranch b = mapperRef.get().apply(row2, meta);
            return Flux.just(a, b);
        });

        StepVerifier.create(adapter.topProductPerBranch(franchiseId))
                .assertNext(tp -> {
                    assertThat(tp.branchId()).isEqualTo(1L);
                    assertThat(tp.branchName()).isEqualTo("Norte");
                    assertThat(tp.productId()).isEqualTo(7L);
                    assertThat(tp.productName()).isEqualTo("Salsa");
                    assertThat(tp.stock()).isEqualTo(20);
                })
                .assertNext(tp -> {
                    assertThat(tp.branchId()).isEqualTo(2L);
                    assertThat(tp.branchName()).isEqualTo("Sur");
                    assertThat(tp.productId()).isEqualTo(5L);
                    assertThat(tp.productName()).isEqualTo("Papas");
                    assertThat(tp.stock()).isEqualTo(15);
                })
                .verifyComplete();

        verify(db).sql(anyString());
        verify(generic).bind("franchiseId", franchiseId);
        verify(generic).map(ArgumentMatchers.any(BiFunction.class));
        verify(fetch).all();
        verifyNoMoreInteractions(db, generic, fetch);
    }
}