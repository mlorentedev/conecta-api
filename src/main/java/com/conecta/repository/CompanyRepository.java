package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByProfessionalFamiliesId(Long familyId);
}