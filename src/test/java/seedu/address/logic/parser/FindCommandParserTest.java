package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertIncorrectCommand;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Priority;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();
    private List<String> names = new ArrayList<>();
    private List<String> addresses = new ArrayList<>();
    private List<String> priorities = new ArrayList<>();

    @Test
    public void parse_emptyArg_failure() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsForName_returnsFindCommand() {
        names.addAll(Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand =
                new FindCommand(names, addresses, priorities);

        // No leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // Multiple whitespaces between successive prefixes
        assertParseSuccess(parser, " n/ \n Alice \n \t n/ Bob  \t", expectedFindCommand);

        // Multiple names specified using one prefix
        assertParseSuccess(parser, " n/Alice | Bob", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgsForName_failure() {
        // Non-alphanumeric characters
        assertParseFailure(parser, " n/%*!",
                String.format(Name.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        // Blank input
        assertParseFailure(parser, " n/",
                String.format(Name.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        // Blank name when using | symbol
        assertParseFailure(parser, " n/ | ",
                String.format(Name.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " n/Alex | ",
                String.format(Name.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " n/ | Alex ",
                String.format(Name.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsForAddress_returnsFindCommand() {
        addresses.addAll(Arrays.asList("Clementi", "Serangoon"));
        FindCommand expectedFindCommand = new FindCommand(names, addresses, priorities);

        // No leading and trailing whitespaces
        assertParseSuccess(parser, " a/Clementi a/Serangoon", expectedFindCommand);

        // Multiple whitespaces between successive prefixes
        assertParseSuccess(parser, " a/ \n Clementi \n  \t   a/ Serangoon \n  \t", expectedFindCommand);

        // Multiple addresses specified using one prefix
        assertParseSuccess(parser, " a/Clementi | Serangoon", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgsForAddress_failure() {
        // Blank input
        assertParseFailure(parser, " a/",
                String.format(Address.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        // Blank address when using | symbol
        assertParseFailure(parser, " a/ | ",
                String.format(Address.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " a/Serangoon | ",
                String.format(Address.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " a/ | Serangoon ",
                String.format(Address.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgsWithOneAddressPrefix_returnsIncorrectFindCommand() {
        addresses.addAll(Arrays.asList("Clementi", "Serangoon"));
        FindCommand expectedFindCommand = new FindCommand(names, addresses, priorities);

        // Only use one a/ prefix for multiple address filters
        assertIncorrectCommand(parser, " a/Clementi Serangoon", expectedFindCommand);
    }

    @Test
    public void parse_validArgsForPriority_returnsFindCommand() {
        priorities.addAll(Arrays.asList("HIGH", "MEDIUM"));
        FindCommand expectedFindCommand = new FindCommand(names, addresses, priorities);

        // No leading and trailing whitespaces
        assertParseSuccess(parser, " pri/HIGH MEDIUM", expectedFindCommand);

        // Multiple whitespaces between given priorities
        assertParseSuccess(parser, " pri/ \n \t HIGH \t \t \n MEDIUM \n \t", expectedFindCommand);

        // Multiple use of priority prefix allowed
        assertParseSuccess(parser, " pri/HIGH pri/MEDIUM", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgsForPriority_failure() {
        // Invalid priority value
        assertParseFailure(parser, " pri/MIDDLE",
                String.format(Priority.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));

        // Blank input
        assertParseFailure(parser, " pri/",
                String.format(Priority.MESSAGE_CONSTRAINTS + "\n" + FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefixes_failure() {
        // Prefix for phone number not allowed
        assertParseFailure(parser, " p/999",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // Prefix for email not allowed
        assertParseFailure(parser, " e/mary@example.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Prefix for remark not allowed
        assertParseFailure(parser, " r/likes baseball",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Prefix for tag not allowed
        assertParseFailure(parser, " t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
