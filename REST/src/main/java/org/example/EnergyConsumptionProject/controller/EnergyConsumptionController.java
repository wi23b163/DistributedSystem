package org.example.EnergyConsumptionProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController

public class EnergyConsumptionController {

   @GetMapping("/energy/current") //returns the percentage of the current hour
    public void getEnergyCurrent() {

   }
   @GetMapping("/energy/historical") //returns the usage date for a given time period (start to end)
    public float getEnergyHistorical(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
       return 0; //Logik noch zu ergänzen
   }
}
