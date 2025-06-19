package org.example.EnergyConsumptionProject.controller;

import org.example.EnergyConsumptionProject.entity.EnergyCurrent;
import org.example.EnergyConsumptionProject.entity.EnergyHistorical;
import org.example.EnergyConsumptionProject.repository.CurrentPercentageRepository;
import org.example.EnergyConsumptionProject.repository.UsageHourlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/energy")
public class EnergyConsumptionController {

    @Autowired
    private CurrentPercentageRepository currentRepo;

    @Autowired
    private UsageHourlyRepository usageRepo;

    @GetMapping("/current")
    public Map<String, Object> getEnergyCurrent() {
        EnergyCurrent entity = currentRepo.findLatest();

        return Map.of(
                "hour", entity.getHour().toString(),
                "communityPool", entity.getCommunityPool(),
                "gridPortion", entity.getGridPortion()
        );
    }



    @GetMapping("/historical")
    public List<EnergyHistorical> getEnergyHistorical(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return usageRepo.findBetween(Timestamp.valueOf(start), Timestamp.valueOf(end));
    }
}
