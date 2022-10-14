package seedu.address.logic.commands.iteration;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_CAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCommissions.CAT_PRODUCER;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;

import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commission.Commission;
import seedu.address.model.customer.Customer;
import seedu.address.model.iteration.Iteration;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.IterationBuilder;


/**
 * Contains integration tests (interaction with the Model and Commissions) for {@code AddIterationCommand}.
 */
public class AddIterationCommandIntegrationTest {

    private static final Supplier<Customer> EMILY_PRODUCER = () -> new CustomerBuilder().withName("Emily").build();
    private static final String CAT_COMMISSION_TITLE = VALID_TITLE_CAT;
    private Model model;

    @BeforeEach
    public void setUp() {
        model = getSetUpModelManager();
    }

    @Test
    public void execute_selectedCommissionAddNewIteration_success() {
        Iteration validIteration = new IterationBuilder().build();

        Model expectedModel = getSetUpModelManager();
        expectedModel.getSelectedCommission().getValue().addIteration(validIteration);

        assertCommandSuccess(new AddIterationCommand(validIteration), model,
                String.format(AddIterationCommand.MESSAGE_ADD_ITERATION_SUCCESS, validIteration, CAT_COMMISSION_TITLE),
                expectedModel);
    }

    @Test
    public void execute_selectedCommissionAddDuplicateIteration_throwsCommandException() {
        Iteration duplicateIteration = new IterationBuilder().build();
        model.getSelectedCommission().getValue().addIteration(duplicateIteration);
        assertCommandFailure(new AddIterationCommand(duplicateIteration), model,
                String.format(AddIterationCommand.MESSAGE_DUPLICATE_ITERATION, CAT_COMMISSION_TITLE));
    }

    @Test
    public void execute_noSelectedCommission_throwsCommandException() {
        Iteration validIteration = new IterationBuilder().build();

        assertCommandFailure(new AddIterationCommand(validIteration),
                new ModelManager(getTypicalAddressBook(), new UserPrefs()),
                Messages.MESSAGE_NO_ACTIVE_COMMISSION);
    }

    private static Model getSetUpModelManager() {
        Customer emily = EMILY_PRODUCER.get();
        Commission emilyCatCommission = CAT_PRODUCER.apply(emily);
        emily.addCommission(emilyCatCommission);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.selectCustomer(emily);
        model.selectCommission(emilyCatCommission);
        return model;
    }
}
