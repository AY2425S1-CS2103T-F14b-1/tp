package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            String[] arr = args.split(",");
            int size = arr.length;
            for (int i = 0; i < size; i++) {
                if (!DeleteCommandParser.isNumber(arr[i].trim())) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }
            }

            int[] temp = new int[size];
            Index[] index = new Index[size];

            // converts string to int
            for (int i = 0; i < size; i++) {
                temp[i] = Integer.parseInt(arr[i].trim());
            }
            // sorts the int in ascending and then converts back to string
            Arrays.sort(temp);
            for (int i = 0; i < size; i++) {
                arr[i] = String.valueOf(temp[i]);
                index[i] = ParserUtil.parseIndex(arr[i]);
            }
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }

}
