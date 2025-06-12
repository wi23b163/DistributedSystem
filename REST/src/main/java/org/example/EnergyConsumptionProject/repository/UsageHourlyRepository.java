package org.example.EnergyConsumptionProject.repository;

import org.example.EnergyConsumptionProject.entity.EnergyHistorical;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UsageHourlyRepository extends CrudRepository<EnergyHistorical, Timestamp> {

    @Query(value = "SELECT * FROM usage_hourly WHERE hour BETWEEN ?1 AND ?2", nativeQuery = true)
    List<EnergyHistorical> findBetween(Timestamp start, Timestamp end);
}

