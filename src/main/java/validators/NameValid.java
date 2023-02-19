package validators;

public class NameValid {
    public static boolean isName (String name)
    {
        return ((name != null) && (!name.equals(""))

                && (name.matches("^[a-zA-Z]*$")));
    }
    public static void isValidName(String name){
        if(!isName(name)){
            throw new IllegalArgumentException(ExceptionBooking.notName);
        }

    }

}
