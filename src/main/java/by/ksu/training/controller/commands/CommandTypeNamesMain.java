package by.ksu.training.controller.commands;

/** names of commands for main menu*/
public enum CommandTypeNamesMain implements CommandType {

    LANGUAGE,
    SHOW,
    VARIANT_ONE_LOCK,
    VARIANT_TWO_CONDITION,
    VARIANT_THREE_BORDER,
    VARIANT_FOUR_COUNT_DOWN_LATCH,
    VARIANT_FIVE_EXECUTOR_SERVICE,
    VARIANT_SIX_FORK_JOIN,
    WRONG_REQUEST;

    public static boolean validate(int number) {
        return  number >= 0 && number < values().length-1;
    }
}
