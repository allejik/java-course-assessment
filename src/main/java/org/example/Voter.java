package org.example;

// Класс Избиратель - для хранения входных данных из файла voters.json для одного избирателя
class Voter {

    // Кандидат за которого проголосовал избиратель
    private String candidateName;

    // Возраст избирателя
    private Integer age;

    // Пол избирателя
    private String gender;

    // Город в котором живет избиратель
    private String residence;

    // Получить имя кандидата за которого проголосовал избиратель
    public String getCandidateName() {
        return candidateName;
    }

    // Получить возраст избирателя
    public Integer getAge() {
        return age;
    }

    // Получить пол избирателя
    public String getGender() {
        return gender;
    }

    // Получить город в котором живет избиратель
    public String getResidence() {
        return residence;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
}

