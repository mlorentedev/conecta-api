package com.conecta.dto;

import java.time.LocalDateTime;

public record ConvocationDTO (Long id, String name, LocalDateTime startDate, LocalDateTime endDate, String schoolYear) {}
