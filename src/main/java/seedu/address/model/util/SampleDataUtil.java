package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");

    public static final LocalDateTime UPDATED_AT = LocalDateTime.of(2024, 1, 1, 0, 0);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), Priority.HIGH, new Remark("Likes baseball"),
                new DateOfBirth(LocalDate.of(1999, 1, 1)), new Income(3000), new FamilySize(1), getTagSet("friends"),
                new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), Priority.MEDIUM, EMPTY_REMARK,
                new DateOfBirth(LocalDate.of(2002, 7, 1)), new Income(1000), new FamilySize(1),
                getTagSet("colleagues", "friends"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), Priority.LOW, EMPTY_REMARK,
                new DateOfBirth(LocalDate.of(1989, 6, 20)), new Income(300), new FamilySize(1),
                getTagSet("neighbours"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), Priority.HIGH, EMPTY_REMARK,
                new DateOfBirth(LocalDate.of(1992, 9, 27)), new Income(1400.00), new FamilySize(1), getTagSet("family"),
                new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), Priority.MEDIUM, EMPTY_REMARK,
                new DateOfBirth(LocalDate.of(2010, 1, 30)), new Income(2000.50), new FamilySize(1),
                getTagSet("classmates"), new UpdatedAt(UPDATED_AT)),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), Priority.LOW, EMPTY_REMARK,
                new DateOfBirth(LocalDate.of(1993, 3, 12)), new Income(1534.69), new FamilySize(1),
                getTagSet("colleagues"), new UpdatedAt(UPDATED_AT))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
