package main.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// a Utility class with helpful static methods
public class Helper {

    //validate password
    public static boolean isValidPassword(String password){

        // minimum length is 8 digits
        if(password.length()>=8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            //check pass for at least 1 letter, number and special char
            return hasLetter.find() && hasDigit.find() && hasSpecial.find();
        }
        else
            return false;
    }
    // validate  email
    public static boolean isValidEmail(String email){
        Pattern special = Pattern.compile("^.+@.+\\..+$");
        Matcher hasSpecial = special.matcher(email);
        return hasSpecial.find();
    }

    //method formats a float to a String in a currency format
    public static String formatCurrency(Float number){
        return "£"+String.format("%.2f", number);
    }

    public static String toCleanNumber(String number){ //TODO regex
        int numberInt = (int) (Float.parseFloat(number)-0);
        return String.valueOf(numberInt);
    }



}
