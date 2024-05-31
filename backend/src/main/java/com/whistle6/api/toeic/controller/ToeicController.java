package com.whistle6.api.toeic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.toeic.model.ToeicDTO;
import com.whistle6.api.toeic.service.ToeicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/toeic")
public class ToeicController {
    private final ToeicService toeicService;
    
    // ==================== Command ====================
    @PostMapping("/register")
    public ResponseEntity<Messenger> register(@RequestBody ToeicDTO ToeicDTO) {
        return ResponseEntity.ok(
            toeicService.save(ToeicDTO)
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Messenger> update(@RequestBody ToeicDTO ToeicDTO) {
        return ResponseEntity.ok(
            toeicService.update(ToeicDTO)
        );
    }

    @PostMapping("/delete")
    public ResponseEntity<Messenger> delete(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(
            toeicService.deleteById(id)
        );
    }

    // ==================== Query ====================

    @GetMapping("/all")
    public ResponseEntity<List<ToeicDTO>> findAll() {
        return ResponseEntity.ok(
            toeicService.findAll()
        );
    }

    @GetMapping("/find")
    public ResponseEntity<ToeicDTO> findById(@RequestParam Long id) {
        return ResponseEntity.ok(
            toeicService.findById(id)
        );
    }

    // ==================== Custom ====================

    @GetMapping("/random")
    public ResponseEntity<Messenger> random() {
        return ResponseEntity.ok(
            toeicService.random()
        );
    }

    @PostMapping("/registerAll")
    public ResponseEntity<Messenger> registerAll(@RequestBody List<ToeicDTO> ToeicDTOs) {
        return ResponseEntity.ok(
            toeicService.saveAll(ToeicDTOs)
        );
    }
}
