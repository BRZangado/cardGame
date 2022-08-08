package com.letscode.cardgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoundDTO {
    private Long roundId;
    private String roundMessage;
    private String player;
    private String movieA;
    private String movieB;
}
