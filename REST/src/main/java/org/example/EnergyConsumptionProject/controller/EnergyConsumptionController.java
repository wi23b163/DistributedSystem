package org.example.EnergyConsumptionProject.controller;
import org.example.EnergyConsumptionProject.entity.EnergyCurrent;
import org.example.EnergyConsumptionProject.entity.EnergyHistorical;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class EnergyConsumptionController {

    @GetMapping("/energy/current")
    public EnergyCurrent getEnergyCurrent() {
        //Hardcoded zum testen
        return new EnergyCurrent(78.54, 7.23);
    }

    @GetMapping("/energy/historical")
    public EnergyHistorical getEnergyHistorical(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        //Hardcoded zum testen
        return new EnergyHistorical(143.024f, 130.101f, 14.75f);
    }
}