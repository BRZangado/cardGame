package com.letscode.cardgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.letscode.cardgame.error.ErrorField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int statusCode;
    private String errorMessage;
    private List<ErrorField> errorFieldList;
    private LocalDateTime timestamp;
}
