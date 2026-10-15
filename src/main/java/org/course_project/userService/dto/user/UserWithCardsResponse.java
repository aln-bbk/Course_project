package org.course_project.userService.dto.user;

import org.course_project.userService.dto.card.PaymentCardSummaryResponse;

import java.io.Serializable;
import java.util.List;

public record UserWithCardsResponse(
    UserResponse user,
    List<PaymentCardSummaryResponse> cards
) implements Serializable {
}
