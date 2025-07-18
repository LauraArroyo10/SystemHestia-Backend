package com.SystemHestia.controller;

import com.SystemHestia.model.Medicine;
import com.SystemHestia.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    private MedicineService service;


    //GET ALL
    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        List<Medicine> medicines = service.getAll();
        if (medicines.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicines);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Medicine medicine = service.findById(id);
        if (medicine == null) {
            return ResponseEntity.status(404).body("No se encontro el medicamento con ID: " + id);
        }
        return ResponseEntity.ok(medicine);
    }


    //POST
    @PostMapping
    public ResponseEntity<Medicine> post(@RequestBody Medicine medicine){
        return ResponseEntity.ok(service.add(medicine));
    }


    //PUT
    @PutMapping
    public ResponseEntity<?> put(@RequestBody Medicine medicine){
        if (service.existsById(medicine.getId())){

            return ResponseEntity.ok(service.update(medicine));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ese id no se encuentra registrado");
    }


    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (service.existsById(id)) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    //PATCH
    @PatchMapping
    public ResponseEntity<?> patch (@RequestBody Medicine medicine) {

        if (service.existsById(medicine.getId())){
            return ResponseEntity.ok(service.edit(medicine));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id "+medicine.getId()+" no esta registrado");
    }


}
