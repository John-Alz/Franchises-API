package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "franchises")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FranchiseEntity {

    @Id
    private String id;
    private String name;
    List<BranchEntity> branchEntities;

}
