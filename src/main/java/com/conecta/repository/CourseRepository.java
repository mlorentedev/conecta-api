package com.conecta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conecta.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByFamilyId(Long familyId);
}