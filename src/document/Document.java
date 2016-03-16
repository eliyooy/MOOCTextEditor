package document;

/** 
 * A class that represents a text document
 * @author UC San Diego Intermediate Programming MOOC team
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

	private String text;
	
	/** Create a new document from the given text.
	 * Because this class is abstract, this is used only from subclasses.
	 * @param text The text of the document.
	 */
	protected Document(String text)
	{
		this.text = text;
	}
	
	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	// This is a helper function that returns the number of syllables
	// in a word.  You should write this and use it in your 
	// BasicDocument class.
	// You will probably NOT need to add a countWords or a countSentences method
	// here.  The reason we put countSyllables here because we'll use it again
	// next week when we implement the EfficientDocument class.
	protected int countSyllables(String word)
	{
		// TODO: Implement this method so that you can call it from the 
	    // getNumSyllables method in BasicDocument (module 1) and 
	    // EfficientDocument (module 2).

		boolean isLoneE;
		int vowelCount = 0;
		String lowerCaseWord = word.toLowerCase();
		String[] letterArray = lowerCaseWord.split("[^aeiouy]+");

		// *** Quantifies number of vowel blocks present in the word ***
		for(int i=0; i<letterArray.length; i++) {
			if (letterArray[i].contains("a") || letterArray[i].contains("e") ||
					letterArray[i].contains("i") || letterArray[i].contains("o") || letterArray[i].contains("u") ||
					letterArray[i].contains("y")) {
				vowelCount +=1;
			}
		}

		// *** Evaluates whether the word ends with a single vowel block that ends with "e" ***
		if(lowerCaseWord.endsWith("e") && vowelCount <= 1) {
			isLoneE = true;
		} else {
			isLoneE = false;
		}

		// *** Returns number of value blocks alternatively if: (a) is a lone E; (b) ends with E, not a lone E,
		// and preceded by a consonant, or (c) is not a lone E and doesn't end in a lone E
		if( isLoneE ) {
			return 1;
		} else if( lowerCaseWord.endsWith("e") &&
				(lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'a' &&
				lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'e' &&
				lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'i' &&
				lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'o' &&
				lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'u' &&
				lowerCaseWord.charAt(lowerCaseWord.length() - 2) != 'y')) {
			String[] vowelArray = word.split("[^aeiouy]+");
			int syllCount = 0;
			for(String vowel : vowelArray) {
				if(vowel.contains("a") || vowel.contains("e") || vowel.contains("i") || vowel.contains("o") ||
						vowel.contains("u") || vowel.contains("y")) {
					syllCount += 1;
				}
			}
			return (syllCount - 1);
		} else {
			String[] vowelArray = lowerCaseWord.split("[^aeiouy]+");
			int syllCount = 0;
			for(String vowel : vowelArray) {
				if(vowel.contains("a") || vowel.contains("e") || vowel.contains("i") || vowel.contains("o") ||
						vowel.contains("u") || vowel.contains("y")) {
					syllCount += 1;
				}
			}
			return syllCount;
		}
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public abstract int getNumWords();
	
	/** Return the number of sentences in this document */
	public abstract int getNumSentences();
	
	/** Return the number of syllables in this document */
	public abstract int getNumSyllables();
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
	    // TODO: Implement this method
	    return 0.0;
	}
	
	
	
}
