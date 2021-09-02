package com.dristy.WeeklyMealSpring.util;

import javax.servlet.http.HttpSession;
import java.util.Objects;


public class Util {

    private static final String USERNAME_ATTRIBUTE = "username";
    
    public static String stringToTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static boolean isValidSession(HttpSession session) {
        if (Objects.isNull(session)) {
            return false;
        }

        String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);

        return Objects.nonNull(username) && !username.isEmpty();
    }

    public static String redirectTo(String url) {
        return String.format("redirect:%s", url);
    }
}
