package com.conecta.dto;

import java.time.LocalDateTime;

public record CommunicationDTO (Long id, LocalDateTime date, String description, Long teacherId, Long employeeId) {}
