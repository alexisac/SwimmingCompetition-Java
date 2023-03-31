package com.example.swimmingcompetition.repository;

import com.example.swimmingcompetition.domain.Account;

public interface RepoAccount extends Repository<Integer, Account> {

    int ifExistAccount(Account ac);
}
