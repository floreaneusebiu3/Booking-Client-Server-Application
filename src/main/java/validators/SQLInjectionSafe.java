package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLInjectionSafe {
    public static boolean containsSQL(String in){
        if(in.contains("select")||in.contains("drop")||in.contains("update")||in.contains("delete"))
            return true;
        if(in.contains("Select")||in.contains("Drop")||in.contains("Update")||in.contains("Delete"))
            return true;
        if(in.contains("SELECT")||in.contains("DROP")||in.contains("UPDATE")||in.contains("DELETE"))
            return true;

    return false;
    }

    public static void isSQLValid(String in){
        if(containsSQL(in)){
            throw new IllegalArgumentException(ExceptionBooking.sqlInjection);
        }

    }
}
