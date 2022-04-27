package com.example;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.dto.AuthRequestDto;
import com.example.dto.AuthResponseDto;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

class PojoAutomaticTest {
    @Test
    void testPojoData() {
        Assertions.assertPojoMethodsForAll(
                        AuthRequestDto.class,
                        AuthResponseDto.class)
                .testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE, Method.CONSTRUCTOR, Method.TO_STRING)
                .areWellImplemented();
    }

    @Test
    void testPojoDomain() {
        Assertions.assertPojoMethodsForAll(
                        Role.class,
                        User.class)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }

}
