package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PRIORITY = "CRITICAL";
    // December is not a valid input, use Dec or dec instead
    private static final String INVALID_DATE_OF_BIRTH = "31 December 2022";
    private static final double INVALID_INCOME = -1929;
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_PRIORITY = BENSON.getPriority().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final double VALID_INCOME = BENSON.getIncome().getValue();
    private static final String VALID_DATE_OF_BIRTH = BENSON.getDateOfBirth().getValue();
    private static final JsonAdaptedAppointment VALID_APPOINTMENT =
            new JsonAdaptedAppointment(BENSON.getAppointment());
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_PRIORITY, VALID_REMARK,
                        VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_PRIORITY, VALID_REMARK,
                        VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Priority.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, INVALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIncome_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, INVALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = Income.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, null, VALID_INCOME, VALID_APPOINTMENT, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_PRIORITY,
                        VALID_REMARK, VALID_DATE_OF_BIRTH, VALID_INCOME, VALID_APPOINTMENT, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}