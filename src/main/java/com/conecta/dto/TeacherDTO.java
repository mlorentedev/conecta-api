package com.conecta.dto;

import java.util.Set;

public record TeacherDTO (Long id, String name, String email, Set<Long> courseIds) {}
