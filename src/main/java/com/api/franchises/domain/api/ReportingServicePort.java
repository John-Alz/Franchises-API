package com.api.franchises.domain.api;

import com.api.franchises.domain.model.TopProductPerBranch;
import reactor.core.publisher.Flux;

public interface ReportingServicePort {

    Flux<TopProductPerBranch> topProductPerBranch(Long franchiseId);

}
