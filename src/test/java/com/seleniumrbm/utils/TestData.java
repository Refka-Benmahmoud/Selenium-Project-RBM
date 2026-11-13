package com.seleniumrbm.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestData {

    public static String uniqueEmail() {
        return "student" + System.currentTimeMillis() + "@mail.com";
    }

    /** "Rs. 1,500" -> 1500 */
    public static int toNumber(String text) {
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public static Map<String, String> defaultAccount() {
        Map<String, String> a = new LinkedHashMap<>();
        a.put("title", "Mr");
        a.put("password", "Test@1234");
        a.put("day", "10");
        a.put("month", "May");
        a.put("year", "1995");
        a.put("firstName", "Ali");
        a.put("lastName", "Ben");
        a.put("address", "1 Main St");
        a.put("country", "Canada");
        a.put("state", "Quebec");
        a.put("city", "Laval");
        a.put("zip", "12345");
        a.put("mobile", "0612345678");
        return a;
    }
}
