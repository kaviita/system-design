package org.partner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.commons.entity.MovieShow;
import org.partner.dto.ShowCreateDto;
import org.partner.service.ShowManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/partner/shows")
@RequiredArgsConstructor
public class PartnerShowController {

    private final ShowManagementService showService;

    @PostMapping
    public ResponseEntity<MovieShow> createNewShow(@Valid @RequestBody ShowCreateDto request) {
        showService.createShow(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PatchMapping("/{id}/timing")
//    public ResponseEntity<MovieShow> updateTiming(@PathVariable Long id, @RequestParam LocalDateTime newStart) {
//        return ResponseEntity.ok(showService.updateShowTiming(id, newStart));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        showService.deleteShow(id);
//        return ResponseEntity.noContent().build();
//    }

}
