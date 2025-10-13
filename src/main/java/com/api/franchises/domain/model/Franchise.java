package com.api.franchises.domain.model;

import java.util.List;

public record Franchise(String id, String name, List<Branch> branches) {
}
