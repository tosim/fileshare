package com.tosim.fileshare.web.service;

public interface UserService {

    void register(String accountName, String password, String confirmPassword, String email);

    void updateUser(String gender, String introduce, Integer id);

}
