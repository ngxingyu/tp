package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalCommissions.DOG_PRODUCER;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commission.Commission;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommissionCommand}.
 */
class DeleteCommissionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noSelectedCustomer_throwsCommandException() {
        // initialise an empty model manager since ModelManager#selectCustomer does not allow me to select null
        model = new ModelManager();
        assertCommandFailure(new DeleteCommissionCommand(INDEX_FIRST), model, Messages.MESSAGE_NO_ACTIVE_CUSTOMER);
    }

    @Test
    public void execute_validIndex_success() {
        model.selectCustomer(model.getFilteredCustomerList().get(0));
        model.getSelectedCustomer().getValue().addCommission(
                DOG_PRODUCER.apply(model.getSelectedCustomer().getValue()));
        Commission commissionToDelete = model.getFilteredCommissionList().get(0);
        DeleteCommissionCommand deleteCommissionCommand = new DeleteCommissionCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommissionCommand.MESSAGE_DELETE_COMMISSION_SUCCESS,
                commissionToDelete);

        CommandResult result = assertDoesNotThrow(() -> deleteCommissionCommand.execute(model));

        assertEquals(result.getFeedbackToUser(), expectedMessage);
        assertFalse(model.getSelectedCustomer().getValue().getCommissions().contains(commissionToDelete));
    }

    @Test
    public void execute_indexTooHigh_throwsCommandException() {
        model.selectCustomer(model.getFilteredCustomerList().get(0));
        DeleteCommissionCommand deleteCommissionCommand = new DeleteCommissionCommand(INDEX_FIRST);
        assertCommandFailure(deleteCommissionCommand, model, Messages.MESSAGE_INVALID_COMMISSION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommissionCommand deleteFirstCommand = new DeleteCommissionCommand(INDEX_FIRST);
        DeleteCommissionCommand deleteSecondCommand = new DeleteCommissionCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommissionCommand deleteFirstCommandCopy = new DeleteCommissionCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // null -> returns false
        assertNotEquals(deleteFirstCommand, null);

        // different commission -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }
}