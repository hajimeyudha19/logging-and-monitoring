package com.ensat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensat.entities.Entities;

@Repository
public interface EntitiesRepository extends JpaRepository<Entities, Integer> {
}
