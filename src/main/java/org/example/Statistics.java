package org.example;

import java.util.Map;

// Интерфейс Статистика - для хранения выходных данных
interface Statistics {

    Map<String, Integer> getCandidateVotes();

    Map<Integer, Integer> getVoterAge();

    Map<String, Integer> getVoterGender();

    Map<String, Integer> getVoterResidence();

    // Установить статистику для количества голосов (ключ - имя кандидата, значение - количетсво голосов)
    void setCandidateVotes(Map<String, Integer> candidateVotes);

    // Установить статистику для возраста избирателей (ключ - возраст, значение - количество избирателей с таких возрастом)
    void setVoterAge(Map<Integer, Integer> voterAge);

    // Установить статистику для пола избирателей (ключ - пол, значение - количество избирателей с таких полом)
    void setVoterGender(Map<String, Integer> voterGender);

    // Установить статистику для городов избирателей (ключ - место жительства, значение - количество избирателей с таких местом жительства)
    void setVoterResidence(Map<String, Integer> voterResidence);
}

