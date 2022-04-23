package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>Класс "Ответ авторизации"</p>
 */
@Data
@AllArgsConstructor
public class AuthResponseDto {

    /**
     * <p>Токен авторизации</p>
     */
    private String token;
}
