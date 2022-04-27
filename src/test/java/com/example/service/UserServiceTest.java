package com.example.service;

import com.example.dao.RoleDao;
import com.example.dao.UserDao;
import com.example.domain.Role;
import com.example.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE_NAME = "ROLE_NAME";

    @Mock
    private UserDao userDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUser() {
        User user = createUser();
        Role userRole = createUserRole();
        when(userDao.findByLogin(user.getLogin())).thenReturn(null);
        when(roleDao.findByName(ROLE_NAME)).thenReturn(userRole);
        when(passwordEncoder.encode(user.getPassword())).thenReturn(PASSWORD);
        when(userDao.save(user)).thenReturn(user);

        User createdUser = userService.saveUser(user);

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(LOGIN, user.getLogin());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    @Test(expected = IllegalStateException.class)
    public void testSaveUserWithError() {
        User user = createUser();
        when(userDao.findByLogin(user.getLogin())).thenReturn(user);
        userService.saveUser(user);
    }

    @Test
    public void testFindByLogin() {
        User user = createUser();
        when(userDao.findByLogin(user.getLogin())).thenReturn(user);

        User createdUser = userService.findByLogin(user.getLogin());

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(LOGIN, user.getLogin());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }


    @Test
    public void testFindByLoginAndPassword() {
        User user = createUser();
        when(userDao.findByLogin(user.getLogin())).thenReturn(user);
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

        User createdUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(LOGIN, user.getLogin());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    private User createUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        return user;
    }

    private Role createUserRole() {
        Role user = new Role();
        user.setId(1L);
        user.setName(ROLE_NAME);
        return user;
    }
}
