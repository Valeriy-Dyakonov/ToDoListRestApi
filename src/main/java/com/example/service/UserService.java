package com.example.service;

import com.example.domain.User;
import org.springframework.stereotype.Component;

/**
 * <p>Интерфейс, описывающий доступный набор функций сервиса работы с пользователями.</p>
 */
@Component
public interface UserService {

    /**
     * <p>Сохранение пользователя в базе данных.</p>
     */
    User saveUser(User user);

    /**
     * <p>Поиск пользователя по логину</p>
     */
    User findByLogin(String login);

    /**
     * <p>Поиск пользователя по логину и паролю</p>
     */
    User findByLoginAndPassword(String login, String password);
}
