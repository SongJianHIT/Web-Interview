package tech.songjian.rpc.producer.impl;


import tech.songjian.rpc.producer.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String findById() {
        return "user{id=1,username=songjian}";
    }
}
