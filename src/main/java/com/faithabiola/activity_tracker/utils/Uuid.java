package com.faithabiola.activity_tracker.utils;

import com.github.javafaker.Faker;

public class Uuid {
    public static String uuid(){
        Faker faker = new Faker();
        return faker.random().hex(10);
    }
}
