package ru.kata.spring.boot_security.demo.service;





import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUsers();

    public User showOneUser(int id);

    public void saveUser(User user, List<Long> roleIds);

    public void updateUser(User updatedUser);

    public void deleteUser(User user);

}
