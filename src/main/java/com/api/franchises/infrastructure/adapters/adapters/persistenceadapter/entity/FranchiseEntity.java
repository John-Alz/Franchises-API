package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "franchises")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseEntity {

    @Id
    private Long id;
    private String name;

}
