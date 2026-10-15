package org.course_project.userService.controller;

import org.course_project.userService.dto.card.PaymentCardCreateRequest;
import org.course_project.userService.dto.card.PaymentCardResponse;
import org.course_project.userService.dto.card.PaymentCardUpdateRequest;
import org.course_project.userService.service.PaymentCardService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class PaymentCardController {

  private final PaymentCardService cardService;

  public PaymentCardController(PaymentCardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping
  public ResponseEntity<PaymentCardResponse> create(
      @Valid @RequestBody PaymentCardCreateRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(cardService.create(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentCardResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(cardService.getById(id));
  }

  @GetMapping
  public ResponseEntity<Page<PaymentCardResponse>> getAll(
      @PageableDefault(
          sort = "id",
          direction = Sort.Direction.ASC
      ) Pageable pageable
  ) {
    return ResponseEntity.ok(cardService.getAll(pageable));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<PaymentCardResponse>> getByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(cardService.getByUserId(userId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PaymentCardResponse> update(
      @PathVariable Long id,
      @Valid @RequestBody PaymentCardUpdateRequest request
  ) {
    return ResponseEntity.ok(cardService.update(id, request));
  }

  @PatchMapping("/{id}/activate")
  public ResponseEntity<PaymentCardResponse> activate(@PathVariable Long id) {
    return ResponseEntity.ok(cardService.activate(id));
  }

  @PatchMapping("/{id}/deactivate")
  public ResponseEntity<PaymentCardResponse> deactivate(@PathVariable Long id) {
    return ResponseEntity.ok(cardService.deactivate(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    cardService.delete(id);
    return ResponseEntity.noContent().build();
  }
}