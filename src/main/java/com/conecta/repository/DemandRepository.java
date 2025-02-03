package com.conecta.repository;

import com.conecta.model.Demand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Long> {
    List<Demand> findByCompanyId(Long companyId);
    List<Demand> findByConvocationId(Long convocationId);
    List<Demand> findByCourseId(Long courseId);
}
