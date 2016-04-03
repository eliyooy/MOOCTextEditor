package textgen;

import java.util.*;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;

	// Testing whether retrain has been run
	private boolean retrain;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method

		// EXCEPTIONS AND ERRORS
		if(!wordList.isEmpty() && !retrain) {
			throw new IllegalArgumentException("Please run retrain before continuing.");
		}

		// STARTING VARIABLES AND STARTER PROCESSING
		String[] textWords = sourceText.split("[ ]+");

		starter = textWords[0];
		ListNode starterNode = new ListNode(starter);
		wordList.add(starterNode);

		// PROCESSING OF ALL WORDS EXCEPT LAST WORD
		for(int i=1; i<textWords.length; i++) {
			String prevWord = textWords[i-1];
			String currWord = textWords[i];
			boolean containsPrevNode = false;
			int prevNodeIndex = 0;

			for( int j=0; j<wordList.size(); j++ ) {
				if( wordList.get(j).getWord().equals(prevWord) ) {
					containsPrevNode = true;
					prevNodeIndex = j;
				}
			}

			if( containsPrevNode ) {
				wordList.get(prevNodeIndex).addNextWord(currWord);
			} else {
				ListNode prevNode = new ListNode(prevWord);
				wordList.add(prevNode);
				wordList.get(wordList.size() - 1).addNextWord(currWord);
			}
		}

		// PROCESSING OF LAST WORD
		String lastWord = textWords[textWords.length - 1];
		boolean containsLastNode = false;
		int lastNodeIndex = 0;

		for( int i=0; i<wordList.size(); i++ ) {
			if( wordList.get(i).getWord().equals(lastWord) ) {
				containsLastNode = true;
				lastNodeIndex = i;
			}
		}

		if ( containsLastNode ) {
			wordList.get(lastNodeIndex).addNextWord(starter);
		} else {
			ListNode lastNode = new ListNode(lastWord);
			wordList.add(lastNode);
			wordList.get(wordList.size() - 1).addNextWord(starter);
		}

	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String output = "";
		String currWord = starter;
		int wordsAdded = 1;

		if(wordList.isEmpty()) {
			return "";
		}

		if(numWords == 0) {
			return "";
		}

		if(wordList.isEmpty()) {
			output = "";
			return output;
		}

		output += currWord;

		while(wordsAdded != numWords) {
			ListNode currNode = wordList.get(0);

			for(int i=0; i<wordList.size(); i++) {
				if( wordList.get(i).getWord().equals(currWord) ) {
					currNode = wordList.get(i);

				}
			}

			String nextWord = currNode.getRandomNextWord(rnGenerator);
			output = output + " " + nextWord;
			currWord = nextWord;
			wordsAdded++;
		}

		System.out.println(wordsAdded);
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		for(int i=0; i<wordList.size(); i++) {
			wordList.remove(i);
		}

		starter = "";

		retrain = true;

		train(sourceText);

	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class

		int randomNumber = generator.nextInt(nextWords.size());

		String chosenWord = nextWords.get(randomNumber);

	    return chosenWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


