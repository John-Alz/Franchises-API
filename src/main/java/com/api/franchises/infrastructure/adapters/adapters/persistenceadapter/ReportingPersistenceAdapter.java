package com.api.franchises.infrastructure.adapters.adapters.persistenceadapter;

import com.api.franchises.domain.model.TopProductPerBranch;
import com.api.franchises.domain.spi.ReportingPersistencePort;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.mapper.ReportingDtoMapper;
import com.api.franchises.infrastructure.adapters.adapters.persistenceadapter.repository.ReportingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class ReportingPersistenceAdapter implements ReportingPersistencePort {

    private final ReportingRepository repository;
    private final ReportingDtoMapper mapper;

    @Override
    public Flux<TopProductPerBranch> topProductPerBranch(Long franchiseId) {
        return repository.findTopProductPerBranchByFranchiseId(franchiseId)
                .map(mapper::toModel);
    }
}
