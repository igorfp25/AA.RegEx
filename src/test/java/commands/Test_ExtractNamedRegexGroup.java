package commands;

import com.automationanywhere.botcommand.commands.ExtractNamedRegexGroup;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Test_ExtractNamedRegexGroup {

    private ExtractNamedRegexGroup command;

    @BeforeClass
    public void setUp() {
        command = new ExtractNamedRegexGroup();
    }

    @Test
    public void testMultipleMatches() {
        String input = "John-25 Jane-30";
        String pattern = "(?<name>\\w+)-(?<age>\\d+)";
        String groupName = "name";

        ListValue result = command.action(input, pattern, groupName, true);
        List<Value> values = result.get();

        Assert.assertEquals(values.size(), 2);
        Assert.assertEquals(((StringValue) values.get(0)).get(), "John");
        Assert.assertEquals(((StringValue) values.get(1)).get(), "Jane");
    }

    @Test
    public void testSingleMatch() {
        String input = "John-25";
        String pattern = "(?<name>\\w+)-(?<age>\\d+)";
        String groupName = "name";

        ListValue result = command.action(input, pattern, groupName, true);
        List<Value> values = result.get();

        Assert.assertEquals(values.size(), 1);
        Assert.assertEquals(((StringValue) values.get(0)).get(), "John");
    }

    @Test
    public void testNoMatch() {
        String input = "No valid data";
        String pattern = "(?<name>\\w+)-(?<age>\\d+)";
        String groupName = "name";

        ListValue result = command.action(input, pattern, groupName, true);
        List<Value> values = result.get();

        Assert.assertTrue(values.isEmpty());
    }

    @Test
    public void testCaseInsensitiveMatch() {
        String input = "john-25";
        String pattern = "(?<name>JOHN)-(?<age>\\d+)";
        String groupName = "name";

        ListValue result = command.action(input, pattern, groupName, false);
        List<Value> values = result.get();

        Assert.assertEquals(values.size(), 1);
        Assert.assertEquals(((StringValue) values.get(0)).get(), "john");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidRegex() {
        String input = "John-25";
        String pattern = "(?<name>\\w+-"; // invalid regex
        String groupName = "name";

        command.action(input, pattern, groupName, true);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidGroupName() {
        String input = "John-25";
        String pattern = "(?<name>\\w+)-(?<age>\\d+)";
        String groupName = "invalidGroup";

        command.action(input, pattern, groupName, true);
    }

    @Test
    public void testNullInputs() {
        ListValue result = command.action(null, null, null, true);
        List<Value> values = result.get();

        Assert.assertTrue(values.isEmpty());
    }
}