package org.course_project.userService.integration;

import org.course_project.userService.entity.User;
import org.course_project.userService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @Transactional
  void saveAndFindByEmail_shouldPersistUser() {

    User user = new User();
    user.setName("Anna");
    user.setSurname("Ivanova");
    user.setBirthDate(LocalDate.of(2000, 5, 15));
    user.setEmail("anna-" + UUID.randomUUID() + "@example.com");

    User savedUser = userRepository.save(user);

    assertThat(savedUser.getId()).isNotNull();

    User foundUser = userRepository.findByEmail(savedUser.getEmail())
        .orElseThrow();

    assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
    assertThat(foundUser.getName()).isEqualTo("Anna");
    assertThat(foundUser.getSurname()).isEqualTo("Ivanova");
    assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
  }
}
