package com.microshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractControllerTest {

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
