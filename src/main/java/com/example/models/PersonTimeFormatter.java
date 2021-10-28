package com.example.models;

import io.quarkus.qute.TemplateExtension;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@TemplateExtension
public class PersonTimeFormatter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

    public static String formattedDate(Person p) {
        return DATE_FORMATTER.format(p.getDateOfCreation()).toLowerCase();
    }
}
