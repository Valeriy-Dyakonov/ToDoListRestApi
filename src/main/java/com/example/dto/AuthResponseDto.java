package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Класс "Ответ авторизации"</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    /**
     * <p>Токен авторизации</p>
     */
    private String token;
}
