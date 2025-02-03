package com.conecta.controller;

import com.conecta.dto.CompanyDTO;
import com.conecta.model.Company;
import com.conecta.service.CompanyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company", description = "Company management endpoints")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Operation(summary = "Get all companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies retrieved successfully",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CompanyDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

}