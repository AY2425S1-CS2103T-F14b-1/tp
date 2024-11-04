package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.scheme.Scheme;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Priority priority;
    private final Remark remark;
    private final DateOfBirth dateOfBirth;
    private final Income income;
    private final FamilySize familySize;
    private final Set<Tag> tags = new HashSet<>();

    private final UpdatedAt updatedAt;

    private final ArrayList<Scheme> schemes = new ArrayList<>();

    /**
     * Constructor for a new person with schemes, only used to AddSchemeCommand.
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Priority priority, Remark remark,
                  DateOfBirth dateOfBirth, Income income, FamilySize familySize, Set<Tag> tags, UpdatedAt updatedAt,
                  ArrayList<Scheme> schemes) {
        requireAllNonNull(name, phone, email, address, priority, remark,
                dateOfBirth, income, familySize, tags, updatedAt, schemes);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.priority = priority;
        this.remark = remark;
        this.dateOfBirth = dateOfBirth;
        this.income = income;
        this.familySize = familySize;
        this.tags.addAll(tags);
        this.updatedAt = updatedAt;
        this.schemes.addAll(schemes);
    }

    /**
     * Constructor for a new person with no schemes. Every field must be present and not null.
     * Allows for the creation of a person without schemes.
     */
    public Person(Name name, Phone phone, Email email, Address address, Priority priority, Remark remark,
                  DateOfBirth dateOfBirth, Income income, FamilySize familySize, Set<Tag> tags, UpdatedAt updatedAt) {
        requireAllNonNull(name, phone, email, address, priority, remark,
                dateOfBirth, income, familySize, tags, updatedAt);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.priority = priority;
        this.remark = remark;
        this.dateOfBirth = dateOfBirth;
        this.income = income;
        this.familySize = familySize;
        this.tags.addAll(tags);
        this.updatedAt = updatedAt;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Priority getPriority() {
        return priority;
    }

    public Remark getRemark() {
        return remark;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public Income getIncome() {
        return income;
    }

    public FamilySize getFamilySize() {
        return familySize;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<Scheme> getSchemes() {
        return schemes;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && priority.equals(otherPerson.priority)
                && remark.equals(otherPerson.remark)
                && dateOfBirth.equals(otherPerson.dateOfBirth)
                && income.equals(otherPerson.income)
                && familySize.equals(otherPerson.familySize)
                && tags.equals(otherPerson.tags)
                && schemes.containsAll(otherPerson.schemes)
                && otherPerson.schemes.containsAll(schemes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, priority, remark,
                dateOfBirth, income, familySize, tags, updatedAt, schemes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("priority", priority)
                .add("remark", remark)
                .add("dateOfBirth", dateOfBirth)
                .add("income", income)
                .add("familySize", familySize)
                .add("tags", tags)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
