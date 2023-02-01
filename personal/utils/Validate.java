package personal.utils;

public class Validate {

    public void checkNumber(String telephone) throws Exception {
        if(!telephone.substring(0,1).equals("+")) {
            throw new PhoneException("Ошибка,номер телефона начинается с +");
        }
        else if(telephone.length() != 12) {
            throw new PhoneException("Номер телефона должна быть 11 цифр");
        }
    }

    public void checkName(String firstName) throws Exception {
        if(firstName.length() == 0) {
            throw new PhoneException("Вы не ввели имя");
        }
        for (int i = 0; i < firstName.length(); i++){
            char c = firstName.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                throw new PhoneException("Введите имя английскими буквами");
            }
        }
    }
}
        
