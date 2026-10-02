package conditional;

import com.automationanywhere.botcommand.conditional.RegexPartialMatchCondition;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test_RegexPartialMatchCondition {

    private RegexPartialMatchCondition condition;

    @BeforeClass
    public void setup() {
        condition = new RegexPartialMatchCondition();
    }

    @Test
    public void testExactMatchCaseSensitive() {
        Boolean result = condition.validate("HelloWorld", "HelloWorld", true);
        Assert.assertTrue(result, "Expected exact match to succeed with case sensitivity");
    }

    @Test
    public void testPartialMatchCaseInsensitive() {
        Boolean result = condition.validate("HelloWorld", "helloworld", false);
        Assert.assertTrue(result, "Expected partial match to succeed ignoring case");
    }

    @Test
    public void testNoMatch() {
        Boolean result = condition.validate("HelloWorld", "ByeWorld", true);
        Assert.assertFalse(result, "Expected no match for different strings");
    }

    @Test
    public void testNullInput() {
        Boolean result = condition.validate(null, "pattern", true);
        Assert.assertFalse(result, "Expected false when input is null");
    }

    @Test
    public void testNullPattern() {
        Boolean result = condition.validate("input", null, true);
        Assert.assertFalse(result, "Expected false when pattern is null");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidRegex() {
        condition.validate("input", "[invalid", true);
    }

    @Test
    public void testDefaultMatchCaseWhenNull() {
        // matchCase is null, should default to true (case-sensitive)
        Boolean result = condition.validate("HelloWorld", "helloworld", null);
        Assert.assertFalse(result, "Expected false when case-sensitive match fails by default");
    }

    @Test
    public void testValidRegexPartialMatch() {
        Boolean result = condition.validate("abc123xyz", "\\d{3}", true);
        Assert.assertTrue(result, "Expected regex to find three consecutive digits");
    }
}