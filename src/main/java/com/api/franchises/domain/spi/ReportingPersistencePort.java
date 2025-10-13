package com.api.franchises.domain.spi;

import com.api.franchises.domain.model.TopProductPerBranch;
import reactor.core.publisher.Flux;

public interface ReportingPersistencePort {

    Flux<TopProductPerBranch> topProductPerBranch(Long franchiseId);

}
