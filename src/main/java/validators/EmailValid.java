package validators;
import jdk.jshell.spi.ExecutionControl;
import model.User;
import java.util.regex.Pattern;

public class EmailValid {

    public static boolean isValid(String mail){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+

                "[a-zA-Z0-9_+&*-]+)*@" +

                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +

                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (mail == null)
            return false;
        return pat.matcher(mail).matches();
    }

    public static void isValidMail(String mail)
    {
        if(!isValid(mail)){
            throw new IllegalArgumentException(ExceptionBooking.mailInvalid);
        }

    }
}
