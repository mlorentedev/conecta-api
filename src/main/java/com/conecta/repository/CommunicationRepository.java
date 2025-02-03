package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.conecta.model.Communication;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
    List<Communication> findByTeacherId(Long teacherId);
    List<Communication> findByEmployeeCompanyId(Long companyId);
    
    @Query("SELECT c FROM Communication c WHERE c.employee.company.professionalFamilies.id = :familyId")
    List<Communication> findByProfessionalFamilyId(Long familyId);
}
