package org.course_project.userService.integration;

import org.course_project.userService.repository.PaymentCardRepository;
import org.course_project.userService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class UserControllerIntegrationTest extends AbstractIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PaymentCardRepository cardRepository;

  @Test
  void createReadUpdateAndDeleteUserThroughHttp() throws Exception {
    String email = email();

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson("Anna", "Ivanova", email)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.email").value(email));

    Long id = userRepository.findByEmail(email)
        .orElseThrow()
        .getId();

    mockMvc.perform(get("/api/users/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Anna"));

    String updatedEmail = email();

    mockMvc.perform(put("/api/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson("Anya", "Ivanova", updatedEmail)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Anya"))
        .andExpect(jsonPath("$.email").value(updatedEmail));

    mockMvc.perform(delete("/api/users/{id}", id))
        .andExpect(status().isNoContent());

    org.assertj.core.api.Assertions
        .assertThat(userRepository.findById(id))
        .isEmpty();
  }

  @Test
  void invalidUserRequestReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name": " ",
                      "surname": "Ivanova",
                      "birthDate": "2000-01-01",
                      "email": "not-an-email"
                    }
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400));
  }

  @Test
  void duplicateEmailReturnsConflict() throws Exception {
    String email = email();

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson("Anna", "Ivanova", email)))
        .andExpect(status().isCreated());

    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson("Ivan", "Petrov", email)))
        .andExpect(status().isConflict());
  }

  @Test
  void missingUserReturnsNotFound() throws Exception {
    mockMvc.perform(get("/api/users/999999999"))
        .andExpect(status().isNotFound());
  }

  private String userJson(
      String name,
      String surname,
      String email) {

    return """
            {
              "name": "%s",
              "surname": "%s",
              "birthDate": "2000-01-01",
              "email": "%s"
            }
            """.formatted(name, surname, email);
  }

  private String email() {
    return "test-" + UUID.randomUUID() + "@example.com";
  }
}