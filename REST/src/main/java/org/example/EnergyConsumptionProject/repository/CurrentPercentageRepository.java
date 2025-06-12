package org.example.EnergyConsumptionProject.repository;

import org.example.EnergyConsumptionProject.entity.EnergyCurrent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface CurrentPercentageRepository extends CrudRepository<EnergyCurrent, Timestamp> {

    @Query(value = "SELECT * FROM current_percentage ORDER BY hour DESC LIMIT 1", nativeQuery = true)
    EnergyCurrent findLatest();
}

