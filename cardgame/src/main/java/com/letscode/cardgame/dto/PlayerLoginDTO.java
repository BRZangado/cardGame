package com.letscode.cardgame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Username não pode ser vazio")
    private String username;
    @NotEmpty(message = "senha não pode ser vazia")
    private String password;
}
