package com.conecta.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.conecta.model.Company;
import com.conecta.repository.CompanyRepository;
import com.conecta.dto.CompanyDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll().stream()
        .collect(Collectors.toList());
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public Company save(CompanyDTO dto) {
        Company newCompany = new Company();
        newCompany.setName(dto.name());
        newCompany.setAddress(dto.address());
        newCompany.setPhone(dto.phone());
        newCompany.setEmail(dto.email());
        return companyRepository.save(newCompany);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}