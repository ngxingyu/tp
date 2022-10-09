package seedu.address.model.commission;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.customer.Customer;
import seedu.address.model.tag.Tag;

/**
 * Represents a Commission in ArtBuddy.
 * Guarantees: except description, all details are present and not null, field values are validated, immutable.
 */
public class Commission {

    // Identity fields
    private final Title title;
    private final Fee fee;
    private final Deadline deadline;
    private final CompletionStatus completionStatus;
    private Customer customer;

    // Data fields
    private final Set<Tag> tags;

    // Optional fields
    private final Description description;

    /**
     * Constructs a Commission.
     * @param builder Instance of CommissionBuilder.
     */
    public Commission(CommissionBuilder builder) {
        title = builder.title;
        fee = builder.fee;
        deadline = builder.deadline;
        completionStatus = builder.status;
        tags = builder.tags;
        description = builder.description;
    }

    public Title getTitle() {
        return title;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public Fee getFee() {
        return fee;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns true if both commissions have the same title.
     * This defines a weaker notion of equality between two commissions.
     */
    public boolean isSameCommission(Commission otherCommission) {
        if (otherCommission == this) {
            return true;
        }

        return otherCommission != null
                && otherCommission.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both commissions have the same fields.
     * This defines a stronger notion of equality between two commissions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Commission)) {
            return false;
        }

        Commission otherCommission = (Commission) other;
        return otherCommission.getTitle().equals(getTitle())
                && otherCommission.getFee().equals(getFee())
                && otherCommission.getDeadline().equals(getDeadline())
                && otherCommission.getTags().equals(getTags())
                && otherCommission.getDescription().equals(getDescription())
                && otherCommission.getCompletionStatus().equals(getCompletionStatus())
                && otherCommission.getCustomer().equals(getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, fee, deadline, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Fee: ")
                .append(getFee())
                .append("; Deadline: ")
                .append(getDeadline())
                .append("; Completed: ")
                .append(getCompletionStatus());

        if (getDescription().isPresent()) {
            builder.append("; Description: ")
                    .append(getDescription().get());
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Builder class for Commission.
     */
    public static class CommissionBuilder {
        // required parameters
        private Title title;
        private Fee fee;
        private Deadline deadline;
        private CompletionStatus status;
        private Set<Tag> tags = new HashSet<>();

        // optional parameters
        private Description description;

        /**
         * Builds CommissionBuilder with all required fields.
         */
        public CommissionBuilder(Title title, Fee fee, Deadline deadline, CompletionStatus status, Set<Tag> tags) {
            requireAllNonNull(title, fee, deadline, status, tags);
            this.title = title;
            this.fee = fee;
            this.deadline = deadline;
            this.status = status;
            this.tags.addAll(tags);
        }

        /**
         * Sets description and returns itself.
         */
        public CommissionBuilder setDescription(Description description) {
            requireNonNull(description);
            this.description = description;
            return this;
        }

        public Commission build() {
            return new Commission(this);
        }
    }
}