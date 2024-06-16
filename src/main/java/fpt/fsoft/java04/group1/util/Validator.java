package fpt.fsoft.java04.group1.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validator {
    Scanner sc = new Scanner(System.in);
    public final String REGEX_PHONE = "^[0-9]{10}$";
    public final String REGEX_NAME = "[a-zA-Z ]+";
    public final String REGEX_ADDRESS = "[a-zA-Z0-9 ,.-/]+";
    public final String REGEX_CONFIRMATION = "^[ynYN]$";
    public final String REGEX_DAY = "^(0[1-9]|[12][0-9]|3[01])$";
    public final String REGEX_MONTH = "^(0?[1-9]|1[0-2])$";
    public final String REGEX_YEAR = "^(19|20)\\d{2}$";


    public String getString(String mess, String error, String regex){
        while(true){
            System.out.print(mess);
            String input = sc.nextLine().trim();
            if(input.isEmpty()){
                System.out.println("Cannot be empty");
            }else{
                if(input.matches(regex)){
                    return input;
                }else{
                    System.out.println(error);
                }
            }
        }
    }

    public int getInt (String mess, String error1, String error2, String error3, int min, int max){
        while(true){
            try{
                System.out.print(mess);
                String input = sc.nextLine().trim();
                if(input.isEmpty()){
                    System.out.println("Cannot be empty");
                }else{
                    int number = Integer.parseInt(input);
                    if(number<min){
                        System.out.println(error1);
                    } else if (number>max) {
                        System.out.println(error2);
                    }else{
                        return number;
                    }
                }
            }catch (Exception e){
                System.out.println(error3);
            }
        }
    }
    public int ValidateId (String mess, String error1, String error2,String regex){
        while(true){
            try{
                System.out.print(mess);
                String input = sc.nextLine().trim();
                if(input.isEmpty()){
                    System.out.println("Cannot be empty");
                }else{
                    int number = Integer.parseInt(input);
                    if (number < 1){
                        System.out.println(error2);
                    }else {
                        return number;
                    }
                }
            }catch (Exception e){
                System.out.println("Id must be Integer");
            }
        }
    }


    public String getDate() {
        while (true) {
            System.out.print("+ Date: ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Cannot be empty");
            }
            else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);

                try {
                    Date currentDate = new Date();
                    Date inputDate = dateFormat.parse(input);
                    currentDate = dateFormat.parse(dateFormat.format(currentDate));
                    return input;
                }
                catch (Exception e) {
                    System.out.println("Wrong day");
                }
            }
        }

    }


}
