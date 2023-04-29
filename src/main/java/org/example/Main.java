package org.example;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {

        FileHelper fileHelper = FileHelper.getInstance();

        // Массив избирателей (см. Voter.java)
        Voter[] voters = fileHelper.readVoters();

        // Ваш код
    }
}