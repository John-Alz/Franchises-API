package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.Franchise;
import com.api.franchises.domain.spi.FranchisePersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.FranchiseEntityMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchisePersistenceAdapter implements FranchisePersistencePort {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseEntityMapper franchiseEntityMapper;

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository.save(franchiseEntityMapper.toEntity(franchise))
                .map(franchiseEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existByName(String name) {
        return franchiseRepository.existsByName(name);
    }

    @Override
    public Mono<Boolean> existById(Long id) {
        return franchiseRepository.existsById(id);
    }

    @Override
    public Mono<Integer> updateNameFranchise(Long franchiseId, String newName) {
        return franchiseRepository.updateNameFranchise(franchiseId, newName);
    }
}
