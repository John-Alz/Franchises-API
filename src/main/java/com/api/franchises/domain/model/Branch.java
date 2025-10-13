package com.api.franchises.domain.model;

import java.util.List;

public record Branch(String id, String name, List<Product> products) {
}
