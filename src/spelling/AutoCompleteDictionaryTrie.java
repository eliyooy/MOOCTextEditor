package spelling;

import java.util.*;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author eliyooy
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size = 0;
	ArrayList<String> dictWords = new ArrayList<>();
	private HashMap<Character, TrieNode> children;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		char[] processedWord = word.toLowerCase().toCharArray();
		TrieNode currentNode = root;
		boolean wordAdded = false;

		for(int i=0; i<processedWord.length; i++) {
			if(!currentNode.getValidNextCharacters().contains(processedWord[i])) {
				currentNode = currentNode.insert(processedWord[i]);
				wordAdded = true;

			} else {
				TrieNode nextNode = currentNode.getChild(processedWord[i]);

				if((i+1) == processedWord.length && !dictWords.contains(word.toLowerCase())) {
					wordAdded = true;
				}

				currentNode = nextNode;

			}
		}

		if(wordAdded) {
			dictWords.add(word.toLowerCase());
			currentNode.setEndsWord(true);
			size++;
		}

	    return wordAdded;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method

	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method

		if(dictWords.contains(s.toLowerCase())) {
			return true;
		}

		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param "text" The text to use at the word stem
     * @param "n" The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions

		 char[] processedWord = prefix.toLowerCase().toCharArray();
		 LinkedList<String> completions = new LinkedList<>();
		 TrieNode currentNode = root;

		 for( char a : processedWord ) {
			 if(currentNode.getChild(a) == null) {
				 return completions;
			 }

			 currentNode = currentNode.getChild(a);
		 }

		 Queue<TrieNode> q = new LinkedList<>();
		 q.add(currentNode);

		 while(!q.isEmpty() && (completions.size()) < numCompletions) {
				TrieNode curr = q.remove();

			 	if(curr != null) {
					if(curr.endsWord()) {
						completions.add(curr.getText());
					}

					Object[] nextChars = curr.getValidNextCharacters().toArray();
					for( Object a : nextChars ) {
						q.add(curr.getChild((char) a));
					}
				}
		 }
    	 
         return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}