package com.api.franchises.domain.model;

import java.util.List;

public record Franchise(Long id, String name, List<Branch> branches) {
}
