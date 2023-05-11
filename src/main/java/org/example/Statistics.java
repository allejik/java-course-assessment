package org.example;

import java.util.Map;

// Интерфейс Статистика - для хранения выходных данных
interface Statistics {

    Map<String, Integer> getCandidateVotes();

    Map<Integer, Integer> getVoterAge();

    Map<String, Integer> getVoterGender();

    Map<String, Integer> getVoterResidence();
}
