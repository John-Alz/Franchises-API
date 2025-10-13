package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {

    private String id;
    private String name;
    private List<ProductEntity> productEntities;

}
