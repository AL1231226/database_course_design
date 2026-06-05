package com.example.Sale_Information_System.Controller;

import com.example.Sale_Information_System.Service.ManufacturerService;
import com.example.Sale_Information_System.pojo.Manufacturer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }
    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }
    @GetMapping("/{id}")
    public Manufacturer getManufacturerById(@PathVariable String id) {
        return manufacturerService.getManufacturerById(id);
    }
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Manufacturer manufacturer) {
        manufacturerService.insert(manufacturer);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Manufacturer manufacturer) {
        manufacturerService.update(manufacturer);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        manufacturerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
