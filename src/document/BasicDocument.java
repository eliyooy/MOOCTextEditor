package document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** 
 * A native implementation of the Document abstract class.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document 
{
	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		//TODO: Implement this method.  See the Module 1 support videos 
	    // if you need help.

		List<String> tokens = getTokens( "[a-zA-Z]+" );

		return tokens.size();
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help.

		String text = this.getText();

		String[] result = text.split("[.!?]+");

		if(text != "") {
			return result.length;
		}
        	return 0;
	}
	
	/**
	 * Get the number of syllables in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for a lone "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help.

		String text = this.getText();
		String[] result = text.split("[ ,.?!0-9():]+");
		int syllCount = 0;

		for(String resulting : result) {
			syllCount += countSyllables(resulting);
		}

		return syllCount;
	}
	
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args)
	{
		// *** First test sentence ***
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		BasicDocument test1 = new BasicDocument("This is a test.  How many???  "
				+ "Senteeeeeeeeeences are here... there should be 5!  Right?");

		// *** Testing the first sentence ***
		String text1 = test1.getText();
		String[] text1a = text1.split("[ ,.?!0-9():]+");
		System.out.println(Arrays.toString(text1a));

		// *** Testing individual words ***
		String hello = "segue";
		String[] wordSplit = hello.split("[^aeiouy]+");
		System.out.println(Arrays.toString(wordSplit));

		BasicDocument testDoc = new BasicDocument("");
		int result = testDoc.countSyllables("segue");
		if(hello.endsWith("e") && hello.charAt(hello.length() - 2) != 'u') {
			System.out.println("You're in the block");
		} else {
			System.out.println("You skipped the block");
		}
		System.out.println(result);


		// *** Remaining Tests ***
		testCase(new BasicDocument(""), 0, 0, 0);
		BasicDocument test2 = new BasicDocument("");

		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		BasicDocument test3 = new BasicDocument("sentence, with, lots, of, commas.!  "
				+ "(And some poaren)).  The output is: 7.5.");

		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		BasicDocument test4 = new BasicDocument("many???  Senteeeeeeeeeences are");

		testCase(new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will."), 49, 33, 3);
		BasicDocument test5 = new BasicDocument("Here is a series of test sentences. Your program should "
				+ "find 3 sentences, 33 words, and 49 syllables. Not every word will have "
				+ "the correct amount of syllables (example, for example), "
				+ "but most of them will.");

		testCase(new BasicDocument("Segue"), 2, 1, 1);
		BasicDocument test6 = new BasicDocument("Segue");

		testCase(new BasicDocument("Sentence"), 2, 1, 1);
		BasicDocument test7 = new BasicDocument("Sentence");

		testCase(new BasicDocument("Sentences?!"), 3, 1, 1);
		BasicDocument test8 = new BasicDocument("Sentences?!");

		testCase(new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad."),
		         32, 15, 1);
		BasicDocument test9 = new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad.");
		
		
	}
	
}
