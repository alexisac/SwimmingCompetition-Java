package com.example.swimmingcompetition.repository;

import com.example.swimmingcompetition.domain.Task;

public interface RepoTask extends Repository<Integer, Task> {

    boolean ifExistTask(Task t);
}
