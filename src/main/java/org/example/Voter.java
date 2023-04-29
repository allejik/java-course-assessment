package org.example;

// Класс Избиратель - для хранения входных данных из файла voters.json для одного избирателя
class Voter {

    // Кандидат за которого проголосовал избиратель
    String candidateName;

    // Возраст избирателя
    Integer age;

    // Пол избирателя
    String gender;

    // Город в котором живет избиратель
    String residence;

    // Получить имя кандидата за которого проголосовал избиратель
    String getCandidateName() {
        return candidateName;
    }

    // Получить возраст избирателя
    Integer getAge() {
        return age;
    }

    // Получить пол избирателя
    String getGender() {
        return gender;
    }

    // Получить город в котором живет избиратель
    String getResidence() {
        return residence;
    }
}

