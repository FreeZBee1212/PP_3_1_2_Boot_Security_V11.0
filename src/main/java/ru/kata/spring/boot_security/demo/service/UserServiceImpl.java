package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService, UserRepository userRepository) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Transactional
    @Override
    public User showOneUser(int id) {
        return userDao.showOneUser(id);
    }

    @Transactional
    @Override
    public void saveUser(User user, List<Long> roleIds) {
        Set<Role> roles = roleIds.stream()
                .map(roleService::getRoleById)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user, List<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            roles.add(roleService.getRoleById(roleId));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }


    @Transactional
    @Override
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }


    public boolean isEmailUnique(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}
