package com.conecta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.ProfessionalFamily;

@Repository
public interface ProfessionalFamilyRepository extends JpaRepository<ProfessionalFamily, Long> {
}