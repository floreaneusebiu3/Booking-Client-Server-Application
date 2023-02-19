package validators;

public class AgeValid {
    public static void isValidAge (int age){
        if(age < 18 ){
            throw new IllegalArgumentException(ExceptionBooking.tooYoung);
        }
        if(age>100)
            throw new IllegalArgumentException(ExceptionBooking.tooOld);
    }
}
