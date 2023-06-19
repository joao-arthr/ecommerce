package br.com.ecommerce.views.utils;

public class Constants {
    private static final String INVALID_CHOICE_MESSAGE = "Invalid choice";
    private static final String RETURNING_MESSAGE = "Returning";

    private static final String ERROR_MESSAGE = "Error: ";
    public static String getInvalidChoiceMessage() {
        return INVALID_CHOICE_MESSAGE;
    }
    public static String getReturningMessage() {
        return RETURNING_MESSAGE;
    }

    public static String getErrorMessage(){return ERROR_MESSAGE;}
}
