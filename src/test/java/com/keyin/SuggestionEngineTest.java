package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SuggestionEngineTest {

    private SuggestionEngine suggestionEngine;

    // Assert that an IOException will be thrown when an
    // invalid filepath is provided.

    @Test
    public void testLoadDictionaryWithInvalidPath() {
        suggestionEngine = new SuggestionEngine();

        Path invalidPath = Paths.get("words.txt");
        IOException ioException = null;
        try {
            suggestionEngine.loadDictionaryData(invalidPath);
        } catch (IOException e) {
            ioException = e;
        }
        Assertions.assertNotNull(ioException);
        System.out.println();
        System.out.println("FileNotFoundException: " + ioException.getMessage());
    }

    // Assert that the program works as expected when special
    // characters are included in the word argument.

    @Test
    public void testWordsWithSpecialCharacters() throws Exception {
        suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("time*"). contains("time"));
        String result = suggestionEngine.generateSuggestions("time*");
        System.out.println();
        System.out.println("Word Suggestions: ");
        System.out.println("-----------------");
        System.out.println(result);
    }

    // Assert that the program does not return suggestions if an exact
    // word match is found in database.

    @Test
    public void testExactWordMatchReturnsEmptyString() throws Exception {
        suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        String result = suggestionEngine.generateSuggestions("time");
        System.out.println();
        System.out.println("Exact Word Match!");
        Assertions.assertEquals("", result);
    }

    // Assert that word suggestions are generated for word inputs that do
    // not have an exact match found in the database.

    @Test
    public void testNonExactWordMatchReturnsSuggestions() throws Exception {
        suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        String result = suggestionEngine.generateSuggestions("timebound");
        System.out.println();
        System.out.println("Word Suggestions: ");
        System.out.println("-----------------");
        System.out.println(result);
        Assertions.assertNotEquals("", result);
    }

    // Assert that the program only returns a max of 10 Top Word Suggestions
    // when the collected word suggestions are greater than 10.

    @Test
    public void testSuggestionLimitOf10() throws Exception{
        suggestionEngine = new SuggestionEngine();
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").getPath()));

        String result = suggestionEngine.generateSuggestions("3dd");
        String[] suggestions = result.split("\n");
        int numSuggestions = suggestions.length;
        Assertions.assertTrue(suggestions.length <= 10);
        System.out.println("Top Suggestions: " + String.join(", ", suggestions));
        System.out.println("The total number of words suggested are " + numSuggestions + ".");
    }

}


