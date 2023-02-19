package validators;
import java.util.regex.*;
public class TelephoneValid {
    public static boolean isNumber(String number){
        String regex = "^([0-9]){10}";
        Pattern p =Pattern.compile(regex);
        if(number==null)
            return false;
        Matcher m=p.matcher(number);
        return m.matches();
    }

    public static void isValidNumber(String number){
        if(!isNumber(number)){
            throw new IllegalArgumentException(ExceptionBooking.notNumber);
        }

    }
}
