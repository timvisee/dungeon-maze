package com.timvisee.dungeonmaze.util;

import net.ricecode.similarity.LevenshteinDistanceStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

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
        if(first == null || second == null)
            return 1.0;

        StringSimilarityService service = new StringSimilarityServiceImpl(new LevenshteinDistanceStrategy());

        double score = service.score(first, second);

        return Math.abs(score - 1.0);
    }
}
