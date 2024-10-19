package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Undoes the latest command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the latest command and reverts person list to the state before it.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo successful:\n%s";
    public static final String MESSAGE_UNDO_ADD = "%s has been unadded from SocialBook";
    public static final String MESSAGE_UNDO_EDIT = "Edits to %s has been reverted";
    public static final String MESSAGE_UNDO_DELETE = "%s have been added back to SocialBook";
    public static final String MESSAGE_UNDO_CLEAR = "Here is the list before clearing";
    private final ArrayList<Command> pastCommands;

    /**
     * Loads the past commands into constructor.
     *
     * @param pastCommands all the past commands during this code run.
     */
    public UndoCommand(ArrayList<Command> pastCommands) {
        this.pastCommands = pastCommands;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (pastCommands.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_LATEST_COMMAND);
        }
        Command latestCommand = pastCommands.get(pastCommands.size() - 1);
        String latestCommandWord = latestCommand.getCommandWord();
        String resultMessage = "";

        switch (latestCommandWord) {
        case "add":
            AddCommand addCommand = (AddCommand) latestCommand;
            Person personToRemove = addCommand.getToAdd();
            model.deletePerson(personToRemove);
            pastCommands.remove(pastCommands.size() - 1);
            resultMessage = String.format(MESSAGE_UNDO_ADD, personToRemove.getName());
            break;
        case "edit":
            EditCommand editCommand = (EditCommand) latestCommand;
            Person bfrEdit = editCommand.getUneditedPerson();
            Person afterEdit = editCommand.getEditedPerson();
            model.setPerson(afterEdit, bfrEdit);
            pastCommands.remove(pastCommands.size() - 1);
            resultMessage = String.format(MESSAGE_UNDO_EDIT, bfrEdit.getName());
            break;

        case "delete":
            DeleteCommand dltCommand = (DeleteCommand) latestCommand;
            ArrayList<Person> personsToAddBack = dltCommand.getPersonsToDelete();

            for (int i = 0; i < personsToAddBack.size(); i++) {
                model.addPerson(personsToAddBack.get(i), dltCommand.getTargetIndexes()[i].getZeroBased());
            }
            String namesAddedBack = personsToAddBack.stream()
                    .map(person -> person.getName().toString())
                    .collect(Collectors.joining(", "));
            pastCommands.remove(pastCommands.size() - 1);
            resultMessage = String.format(MESSAGE_UNDO_DELETE, namesAddedBack);
            break;

        case "clear":
            ClearCommand clearCommand = (ClearCommand) latestCommand;
            model.setAddressBook(clearCommand.getModel().getAddressBook());
            model.setUserPrefs(clearCommand.getModel().getUserPrefs());
            pastCommands.remove(pastCommands.size() - 1);
            resultMessage = MESSAGE_UNDO_CLEAR;
            break;
        default:
            pastCommands.remove(pastCommands.size() - 1);
            break;

        }
        return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, resultMessage));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
