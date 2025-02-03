package com.conecta.dto;

import java.util.Set;

public record CompanyDTO (Long id, String name, String address, String phone, String email, Set<Long> professionalFamilyIds) {}