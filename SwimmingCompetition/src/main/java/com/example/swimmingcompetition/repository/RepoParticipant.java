package com.example.swimmingcompetition.repository;

import com.example.swimmingcompetition.domain.Participant;

public interface RepoParticipant extends Repository<Integer, Participant> {

    boolean ifExistParticipant(Participant p);
}
