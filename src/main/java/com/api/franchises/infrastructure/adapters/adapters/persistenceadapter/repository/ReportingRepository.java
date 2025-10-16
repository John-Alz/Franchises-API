package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository;


import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.dto.TopProductPerBranchRow;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;

public interface ReportingRepository extends Repository<BranchEntity, Long> {

    @Query("""
    WITH ranked AS (
      SELECT b.id   AS branchid,     
             b.name AS branchname,
             p.id   AS productid,
             p.name AS productname,
             p.stock AS stock,
             ROW_NUMBER() OVER (
               PARTITION BY b.id
               ORDER BY p.stock DESC, p.id ASC
             ) AS rn
      FROM branches b
      JOIN products p ON p.branch_id = b.id
      WHERE b.franchise_id = :franchiseId
    )
    SELECT branchid, branchname, productid, productname, stock
    FROM ranked
    WHERE rn = 1
    ORDER BY branchname ASC, branchid ASC
    """)
    Flux<TopProductPerBranchRow> findTopProductPerBranchByFranchiseId(Long franchiseId);



}
