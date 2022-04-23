package com.example.service;

import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.domain.Role;
import com.example.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>Сервис работы с пользователями.</p>
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    /**
     * <p>Класс для работы с сущностью пользователей.</p>
     */
    private final UserDao userDao;

    /**
     * <p>Класс для работы с сущностью ролей.</p>
     */
    private final RoleDao roleDao;

    /**
     * <p>Стандартный класс для работы с шифрованием паролей.</p>
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * <p>Сохранение пользователя в базе данных.</p>
     * <p>Пользователю по умолчанию задаётся роль "ROLE_USER". Пароль шифруется.</p>
     *
     * @param user пользователь.
     * @return User, сохраненный пользователь.
     */
    @Override
    public User saveUser(User user) {
        if (userDao.findByLogin(user.getLogin()) != null) {
            throw new IllegalStateException(String.format("Login [%s] already exists", user.getLogin()));
        }
        Role userRole = roleDao.findByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    /**
     * <p>Поиск пользователя по логину</p>
     *
     * @param login логин пользователь.
     * @return User, найденный пользователь.
     */
    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    /**
     * <p>Поиск пользователя по логину и паролю</p>
     *
     * @param login    логин пользователь.
     * @param password пароль пользователя.
     * @return User, найденный пользователь.
     */
    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        return user != null && passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }
}
