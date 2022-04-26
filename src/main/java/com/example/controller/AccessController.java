package com.example.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccessController {

    @GetMapping("/hasAccess")
    @ApiOperation("Check if user has access")
    public Boolean checkAccess() {
        return true;
    }
}
