package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ReportingPersistenceAdapter implements ReportingPersistencePort {

    private final DatabaseClient db;

    @Override
    public Flux<TopProductPerBranch> topProductPerBranch(Long franchiseId) {
        String sql = """
      WITH ranked AS (
        SELECT b.id   AS branch_id,
               b.name AS branch_name,
               p.id   AS product_id,
               p.name AS product_name,
               p.stock AS stock,
               ROW_NUMBER() OVER (
                 PARTITION BY b.id
                 ORDER BY p.stock DESC, p.id ASC
               ) AS rn
        FROM branches b
        JOIN products p
          ON p.branch_id = b.id
        WHERE b.franchise_id = :franchiseId
      )
      SELECT branch_id, branch_name, product_id, product_name, stock
      FROM ranked
      WHERE rn = 1
      ORDER BY branch_name ASC, branch_id ASC
      """;

        return db.sql(sql)
                .bind("franchiseId", franchiseId)
                .map((row, meta) -> new TopProductPerBranch(
                        row.get("branch_id", Long.class),
                        row.get("branch_name", String.class),
                        row.get("product_id", Long.class),
                        row.get("product_name", String.class),
                        row.get("stock", Integer.class)
                ))
                .all();
    }
}
