package com.example.dto;

import lombok.Data;

/**
 * <p>Класс "Запрос авторизации"</p>
 */
@Data
public class AuthRequestDto {

    /**
     * <p>Логин пользователя</p>
     */
    private String login;

    /**
     * <p>Пароль пользователя</p>
     */
    private String password;
}
