package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuiValid {
    public static boolean isCui(String cui){
        String regex = "^([0-9]){12}";
        Pattern p =Pattern.compile(regex);
        if(cui==null)
            return false;
        Matcher m=p.matcher(cui);
        return m.matches();
    }

    public static void isValidCui(String cui){
        if(isCui(cui)){
            throw new IllegalArgumentException(ExceptionBooking.notCui);
        }
    }
}
