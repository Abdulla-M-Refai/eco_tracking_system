package com.eco.tracking.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eco.tracking.system.entity.EnvironmentalData;

public interface EnvironmentalDataRepository extends JpaRepository<EnvironmentalData, Long>
{

}
