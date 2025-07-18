import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberTypeChecker {

    public static void main(String[] args) {
        String phoneNumber = "(248) 923-3456";
        String phoneNumberType = checkPhoneNumberType(phoneNumber);
        System.out.println("Phone number type: " + phoneNumberType);
    }

    public static String checkPhoneNumberType(String phoneNumber) {
        // Define regular expressions for mobile, landline, and VoIP patterns
        String mobilePattern = "\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})";
        String landlinePattern = "\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})";
        String voipPattern = "\\(?(\\d{3})\\)?[-.\\s]?(\\d{3})[-.\\s]?(\\d{4})";

        // Compile the regular expressions
        Pattern mobileRegex = Pattern.compile(mobilePattern);
        Pattern landlineRegex = Pattern.compile(landlinePattern);
        Pattern voipRegex = Pattern.compile(voipPattern);

        // Match the phone number with each pattern
        Matcher mobileMatcher = mobileRegex.matcher(phoneNumber);
        Matcher landlineMatcher = landlineRegex.matcher(phoneNumber);
        Matcher voipMatcher = voipRegex.matcher(phoneNumber);

        // Check for matches and determine the phone number type
        if (mobileMatcher.matches() && phoneNumber.startsWith("1")) {
            return "Mobile";
        } else if (landlineMatcher.matches() && phoneNumber.startsWith("1")) {
            return "Landline";
        } else if (voipMatcher.matches() && phoneNumber.startsWith("1")) {
            return "VoIP";
        } else {
            return "Unknown";
        }
    }
}
