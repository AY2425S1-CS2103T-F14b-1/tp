package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_DELETE_SCHEME_PERSON_SUCCESS;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_DELETE_SCHEME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FamilySize;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.person.UpdatedAt;
import seedu.address.model.scheme.Scheme;
import seedu.address.model.tag.Tag;

/**
 * Deletes a scheme from a person in the address book.
 */
public class DeleteSchemeCommand extends Command {
    public static final String COMMAND_WORD = "deletescheme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a scheme from the family identified by the "
            + "index number used in the displayed family list.\n"
            + "This command can only be used after Scheme command.\n"
            + "Parameters: PERSON_INDEX SCHEME_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 i/1";
    private final Index personIndex;
    private final ArrayList<Index> schemeIndex = new ArrayList<>();

    private final ArrayList<Scheme> schemesToBeDeleted = new ArrayList<>();

    private final ArrayList<Scheme> newSchemes = new ArrayList<>();

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param personIndex of the person in the filtered person list to edit
     * @param schemeIndex of the scheme in the person to be deleted
     */
    public DeleteSchemeCommand(Index personIndex, ArrayList<Index> schemeIndex) {
        requireAllNonNull(personIndex, schemeIndex);
        this.personIndex = personIndex;
        this.schemeIndex.addAll(schemeIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size() || personIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToEdit = lastShownList.get(personIndex.getZeroBased());
        editedPerson = deleteSchemeFromPerson(personToEdit, schemeIndex);

        if (!personToEdit.isSamePerson(editedPerson)) {
            if (model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.updateAppointments(personToEdit.getName(), editedPerson.getName());
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_SCHEME_PERSON_SUCCESS, editedPerson.getName()));

    }

    /**
     * Deletes the scheme from the person.
     * @param targetFamily the person to be edited
     * @param schemeIndex the index of the scheme to be deleted
     * @return the edited person
     * @throws CommandException if the index is invalid
     */
    private Person deleteSchemeFromPerson(Person targetFamily, ArrayList<Index> schemeIndex) throws CommandException {
        ArrayList<Scheme> currentSchemes = targetFamily.getSchemes();
        for (Index index : schemeIndex) {
            if (index.getZeroBased() >= currentSchemes.size() || index.getZeroBased() < 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_SCHEMES_DISPLAYED_INDEX);
            }
            schemesToBeDeleted.add(currentSchemes.get(index.getZeroBased()));
        }
        newSchemes.addAll(currentSchemes);
        newSchemes.removeAll(schemesToBeDeleted);

        Name updatedName = targetFamily.getName();
        Phone updatedPhone = targetFamily.getPhone();
        Email updatedEmail = targetFamily.getEmail();
        Address updatedAddress = targetFamily.getAddress();
        Priority updatedPriority = targetFamily.getPriority();
        Remark updatedRemark = targetFamily.getRemark();
        DateOfBirth updatedDateOfBirth = targetFamily.getDateOfBirth();
        Income updatedIncome = targetFamily.getIncome();
        FamilySize updatedFamilySize = targetFamily.getFamilySize();
        Set<Tag> updatedTags = targetFamily.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPriority, updatedRemark,
                updatedDateOfBirth, updatedIncome, updatedFamilySize, updatedTags, UpdatedAt.now(), newSchemes);
    }

    public String getCommandWord() {
        return COMMAND_WORD;
    }

    public Person getUneditedPerson() {
        return personToEdit;
    }

    public Person getEditedPerson() {
        return editedPerson;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        Person beforeEdit = this.getUneditedPerson();
        Person afterEdit = this.getEditedPerson();
        model.setPerson(afterEdit, beforeEdit);
        pastCommands.remove();
        return String.format(MESSAGE_UNDO_DELETE_SCHEME, beforeEdit.getName());
    }
}
