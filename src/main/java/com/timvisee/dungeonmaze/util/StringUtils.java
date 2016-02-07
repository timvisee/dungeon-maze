package com.timvisee.dungeonmaze.util;

import net.ricecode.similarity.LevenshteinDistanceStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils {

    /**
     * Get the difference value of a string.
     *
     * @param first First value.
     * @param second Second value.
     *
     * @return The string difference value.
     */
    public static double getDifference(String first, String second) {
        // Make sure both strings are equal
        if(first == null || second == null)
            return 1.0;

        // Create a string comparison server
        StringSimilarityService service = new StringSimilarityServiceImpl(new LevenshteinDistanceStrategy());

        // Determine the difference score and return it's absolute value
        return Math.abs(service.score(first, second) - 1.0);
    }

    /**
     * Get a full stack trace of an exception as a string.
     *
     * @param exception The exception.
     *
     * @return Stack trace as a string.
     */
    public static String getStackTrace(Exception exception) {
        // Create a string and print writer to print the stack trace into
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        // Print the stack trace into the print writer
        exception.printStackTrace(printWriter);

        // Return the result as a string
        return stringWriter.toString();
    }
}
