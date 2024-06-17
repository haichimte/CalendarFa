package fpt.fsoft.java04.group1.common;

import fpt.fsoft.java04.group1.dao.UserDao;
import fpt.fsoft.java04.group1.model.User;
import fpt.fsoft.java04.group1.util.Validator;

import java.util.Scanner;

public class UserCommon {
    public static User loggedInUser;
    public User loginCommon() {
        Scanner sc = new Scanner(System.in);
        Validator va = new Validator();

        //// lam tiep vao day
        UserDao dao = new UserDao();
        boolean check = false;
        while (!check){
            try{
                System.out.print("Enter email: ");
                String email = va.getEmail("Wrong format email",va.EMAIL_REGEX);
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                User user = dao.login(email,password);
                if (user != null){
                    loggedInUser = user;
                    check = true;
                    System.out.println("Wellcom "+ user.getUserName());
                }else {
                    System.out.println("Invalid email or password");
                }
                return user;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return null;

    }
}
