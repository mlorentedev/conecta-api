package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Convocation;

@Repository
public interface ConvocationRepository extends JpaRepository<Convocation, Long> {
    List<Convocation> findBySchoolYear(String schoolYear);
}