package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AnyTagCustomerCommand;
import seedu.address.model.customer.CustomerContainsAnyTagPredicate;
import seedu.address.model.tag.Tag;

public class AnyTagCustomerCommandParserTest {

    private AnyTagCustomerCommandParser parser = new AnyTagCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnyTagCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAnyTagCustomerCommand() {
        // no leading and trailing whitespaces
        List<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("tag1"));
        tagList.add(new Tag("tag 2"));
        tagList.add(new Tag("tag 3"));
        AnyTagCustomerCommand expectedAnyTagCustomerCommand =
                new AnyTagCustomerCommand(new CustomerContainsAnyTagPredicate(tagList));
        assertParseSuccess(parser, " t/tag1 t/tag 2 t/tag 3", expectedAnyTagCustomerCommand);
    }

}
