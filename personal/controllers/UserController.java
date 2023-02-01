package personal.controllers;

import personal.model.Fields;
import personal.model.Repository;
import personal.model.User;
import personal.utils.Validate;

import java.util.List;

public class UserController {
    private final Repository repository;
    private final Validate validate;
    
    public UserController(Repository repository, Validate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public List<User> getUsers() throws Exception {
        return repository.getAllUsers();
    }

    public User readUser(String userId) throws Exception {
        List<User> users = repository.getAllUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        throw new Exception("Пользователь не найден");
    }

    public void saveUser(User user) throws Exception {
        validate.checkNumber(user.getPhone());
        validate.checkName(user.getFirstName());
        repository.CreateUser(user);
    }

    public void updateUser(User user, Fields field, String param) throws Exception {
        if(field == Fields.TELEPHONE) {
            validate.checkNumber(param);
        }
        repository.UpdateUser(user, field, param);
    }

    public  User deleteUser(String userId) throws Exception {
        List<User> users = repository.getAllUsers();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                repository.delUser(users);
                return user;
            }
        }
        throw new Exception("Пользователь не найден");
    }
}
