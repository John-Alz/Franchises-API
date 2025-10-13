package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Branch;
import com.api.franchises.domain.spi.BranchPersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.entity.BranchEntity;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.BranchEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchPersistenceAdapter implements BranchPersistencePort {

    private final BranchRepository branchRepository;
    private final BranchEntityMapper branchEntityMapper;

    @Override
    public Mono<Branch> saveBranch(Long franchiseId, Branch branch) {
        return branchRepository.save(branchEntityMapper.toEntity(branch))
                .map(branchEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existById(Long id) {
        return branchRepository.existsById(id);
    }
}
