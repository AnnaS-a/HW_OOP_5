package personal.views;

import personal.controllers.UserController;
import personal.model.Fields;
import personal.model.User;
import personal.utils.PhoneException;
import personal.utils.Validate;
import java.util.Scanner;

public class ViewUser {

    private final UserController userController;
    private final Validate validate;

    public ViewUser(UserController userController, Validate validate) {
        this.userController = userController;
        this.validate = validate;
    }

    public void run() {
        Commands com = Commands.NONE;
        showHelp();
        while (true) {
            try {
                String command = prompt("Введите команду: ");
                com = Commands.valueOf(command.toUpperCase());
                if (com == Commands.EXIT)
                    return;
                switch (com) {
                    case CREATE:
                        create();
                        break;
                    case READ:
                        read();
                        break;
                    case UPDATE:
                        update();
                        break;
                    case DELETE:
                        delete();
                        break;
                    case LIST:
                        list();
                        break;
                    case HELP:
                        showHelp();
                }
            } catch (Exception ex) {
                System.out.println("Произошла ошибка " + ex.toString());
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private void showHelp() {
        System.out.println("Список команд:");
        for (Commands c : Commands.values()) {
            System.out.println(c);
        }
    }

    private void list() throws Exception {
        try {
            for (User user : userController.getUsers()) {
                System.out.println(user);
            }
        } catch (PhoneException ex) {
            System.out.println("Произошла ошибка " + ex.toString());
        }
    }

    private void read() throws Exception {
        try {
            String id = prompt("Идентификатор пользователя: ");
            User user_ = userController.readUser(id);
            System.out.println(user_);
        } catch (PhoneException ex) {
            System.out.println("Произошла ошибка " + ex.toString());
        }
    }

    private void create() throws Exception {
        try {
            String firstName = prompt("Имя: ");
            validate.checkName(firstName);
            String lastName = prompt("Фамилия: ");
            String phone = null;
            phone = catchTelephone(phone);
            if (phone == null) {
                return;
            }
            userController.saveUser(new User(firstName, lastName, phone));
        } catch (PhoneException ex) {
            System.out.println("Произошла ошибка " + ex.toString());
        }
    }

    public String catchTelephone(String telephone) throws Exception {
        while (true) {
            try {
                telephone = prompt("Введите номер телефона (Отказ введите 0): ");
                if (telephone.equals("0")) {
                    System.out.println("Вы отказались от ввода для изменения пользователя");
                    return null;
                }
                validate.checkNumber(telephone);
                return telephone;
            } catch (PhoneException ex) {
                System.out.println("Произошла ошибка " + ex.toString());
            }
        }
    }

    private void update() throws Exception {
        try {
            String userid = prompt("Идентификатор пользователя: ");
            String field_name = prompt("Какое поле (LASTNAME,NAME,TELEPHONE): ");
            String param = null;
            if (Fields.valueOf(field_name.toUpperCase()) == Fields.TELEPHONE) {
                param = catchTelephone(param);
                if (param == null) {
                    return;
                }
            } else {
                param = prompt("Введите на то что хотите изменить: ");
            }
            User _user = userController.readUser(userid);
            userController.updateUser(_user, Fields.valueOf(field_name.toUpperCase()), param);
        } catch (PhoneException ex) {
            System.out.println("Произошла ошибка " + ex.toString());
        }
    }

    private void delete() throws Exception {
        try {
            String id = prompt("Введите идентификатор пользователя, которого хотите удалить: ");
            System.out.println("Хотите удалить: ");
            System.out.println(userController.readUser(id));
            String consent = prompt("Введите y - удалить, n - не удалять: ");
            if (consent.equals("y")) {
                userController.deleteUser(id);
            } else {
                System.out.println("Удаление отклонено.");
            }
        } catch (PhoneException ex) {
            System.out.println("Произошла ошибка " + ex.toString());
        }
    }
}