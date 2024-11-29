package lk.ijse.Regex;

import java.util.regex.Pattern;

public class RegexFactory {
    private static RegexFactory regexFactory;

    // User patterns
    private final Pattern username;
    private final Pattern password;
    private final Pattern userrole;

    // Student patterns
    private final Pattern student_id;
    private final Pattern student_name;
    private final Pattern address;

    // Course patterns
    private final Pattern course_id;
    private final Pattern course_name;
    private final Pattern course_fee;

    // Common patterns
    private final Pattern email;
    private final Pattern phone_number;

    private RegexFactory() {
        username = Pattern.compile("^[a-zA-Z]+$");
        password = Pattern.compile("^\\d{8}$");
        userrole = Pattern.compile("(?i)^(Admin|AdmissionsCoordinator)$");
        student_id = Pattern.compile("^S00([1-9]|[1-9]\\d{1,2}|1000)$");
        student_name = Pattern.compile("^[a-zA-Z\\s]+$");
        address = Pattern.compile("^.{6,}$");
        course_id = Pattern.compile("^C00([1-9]|[1-9]\\d{1,2}|1000)$");
        course_name = Pattern.compile("^.{6,}$");
        course_fee = Pattern.compile("^(\\d{5,}(\\.\\d{1,2})?|\\d+\\.\\d{1,2})$");
        email = Pattern.compile("^[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com)$");
        phone_number = Pattern.compile("^0\\d{9}$");
    }

    public static RegexFactory getInstance() {
        return regexFactory == null ? regexFactory = new RegexFactory() : regexFactory;
    }

    public Pattern getPattern(RegexType regexType) {
        return switch (regexType) {
            case USERNAME -> username;
            case PASSWORD -> password;
            case USERROLE -> userrole;
            case STUDENT_ID -> student_id;
            case STUDENT_NAME -> student_name;
            case ADDRESS -> address;
            case COURSE_ID -> course_id;
            case COURSE_NAME -> course_name;
            case COURSE_FEE -> course_fee;
            case EMAIL -> email;
            case PHONE_NUMBER -> phone_number;
            default -> throw new RuntimeException("Pattern not found");
        };
    }
}
