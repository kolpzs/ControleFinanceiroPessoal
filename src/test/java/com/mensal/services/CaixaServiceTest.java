package com.mensal.services;

import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaixaServiceTest {

    @Autowired
    private CaixaService caixaService;

    @Test
    @DisplayName("teste save")
    void testSave() {

    }
}