package org.course_project.userService.controller;

import org.course_project.userService.dto.user.UserCreateRequest;
import org.course_project.userService.dto.user.UserResponse;
import org.course_project.userService.dto.user.UserUpdateRequest;
import org.course_project.userService.service.UserService;
import org.course_project.userService.dto.user.UserWithCardsResponse;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponse> create(
      @Valid @RequestBody UserCreateRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.create(request));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping
  public ResponseEntity<Page<UserResponse>> getAll(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String surname,
      @PageableDefault(
          sort = "id",
          direction = Sort.Direction.ASC
      ) Pageable pageable
  ) {
    return ResponseEntity.ok(
        userService.getAll(name, surname, pageable)
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateRequest request
  ) {
    return ResponseEntity.ok(userService.update(id, request));
  }

  @PatchMapping("/{id}/activate")
  public ResponseEntity<UserResponse> activate(@PathVariable Long id) {
    return ResponseEntity.ok(userService.activate(id));
  }

  @PatchMapping("/{id}/deactivate")
  public ResponseEntity<UserResponse> deactivate(@PathVariable Long id) {
    return ResponseEntity.ok(userService.deactivate(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/with-cards")
  public ResponseEntity<UserWithCardsResponse> getWithCards(
      @PathVariable Long id
  ) {
    return ResponseEntity.ok(userService.getWithCards(id));
  }

  @GetMapping("/active")
  public ResponseEntity<java.util.List<UserResponse>> getActiveByName(
      @RequestParam String name
  ) {
    return ResponseEntity.ok(userService.getActiveByName(name));
  }

  @GetMapping("/without-cards")
  public ResponseEntity<java.util.List<UserResponse>> getUsersWithoutCards() {
    return ResponseEntity.ok(userService.getUsersWithoutCards());
  }

  @GetMapping("/by-email")
  public ResponseEntity<UserResponse> getByEmail(@RequestParam String email) {
    return ResponseEntity.ok(userService.getByEmail(email));
  }

}