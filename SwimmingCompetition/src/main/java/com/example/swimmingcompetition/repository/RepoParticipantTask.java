package com.example.swimmingcompetition.repository;

import com.example.swimmingcompetition.domain.ParticipantTask;

import java.util.Vector;

public interface RepoParticipantTask extends Repository<Integer, ParticipantTask> {

    boolean ifExist(ParticipantTask pt);

    Vector<ParticipantTask> findAllParticipants(int idTask);

    Vector<ParticipantTask> findAllTasks(int idParticipant);
}
