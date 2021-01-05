package textprocessing;

import shared.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie {
    private Node[] root;
    private int nodeCount;
    private int wordCount;
    private int ALPHABETSIZE = 26;

    public Trie() {
        this.root = new Node[ALPHABETSIZE];
        this.nodeCount = 1;
        this.wordCount = 0;
    }

    public Node[] getRoot() {
        return root;
    }

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     *
     * @param queries The query being added to the trie
     */
    public void add(List<Query> queries) {
        Node[] current = root;
        Node endNode = null;
        for (int i = 0; i < queries.size(); i++) {
            String word = queries.get(i).toString();
            for (int j = 0; j < word.length(); j++) {
                char c = word.charAt(j);
                if (Character.isAlphabetic(c)) {
                    int index = c - 'a';
                    if (current[index] == null) {
                        Node n = new Node();
                        current[index] = n;
                        endNode = n;
                        incrNodeCount();
                        current = n.getNodes();
                    } else {
                        endNode = current[index];
                        current = current[index].getNodes();
                    }
                } else {
                    if (endNode != null) {
                        endNode.setComplete(true);
                        incrWordCount();
                    }
                }
            }
            if (endNode != null) {
                endNode.setComplete(true);
            }
            incrWordCount();
            current = root;
            endNode = null;
        }
    }

    /**
     * Searches the trie for the specified word
     *
     * @param query The word being searched for
     *
     * @return A reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public List<String> findSuggestions(String query) {
        Node[] current = root;
        Node endNode = null;
        List<String> suggestions = new ArrayList<>();
        for (int i = 0; i < query.length(); i++) {
            char c = query.charAt(i);
            if (Character.isAlphabetic(c)) {
                int index = c - 'a';
                if (current[index] == null) {
                    return null;
                } else {
                    endNode = current[index];
                    current = current[index].getNodes();
                }
            }
        }
        if (endNode != null) {
            if (endNode.isComplete) {
                for (int j = 0; j < ALPHABETSIZE; j++) {
                    char c = (char)('a' + j);
                    Set<String> words = outputWord(new HashSet<String>(), new StringBuilder(), current[j], c);
                    for (String w : words) {
                        String sug = query + " " + w;
                        if (!sug.equals(query)) {
                            suggestions.add(sug);
                        }
                    }
                }
            }
        }
        return suggestions;
    }

    /**
     * Returns the number of unique words in the trie
     *
     * @return The number of unique words in the trie
     */
    public int getWordCount() {
        return wordCount;
    }

    public void incrWordCount() { wordCount++; }

    public void incrNodeCount() {   nodeCount++; }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    public int getNodeCount() {
        return nodeCount;
    }

    public Set<String> outputWord(Set<String> words, StringBuilder sb, Node n, char c) {
        if (n == null) {
            return words;
        }
        StringBuilder newStrBldr = new StringBuilder();
        newStrBldr.append(sb.toString() + c);
        if (n.isComplete) {
            words.add(newStrBldr.toString());
            return words;
        }
        Node[] current = n.getNodes();
        for (int i = 0; i < ALPHABETSIZE; i++) {
            int index = 'a' + i;
            char x = (char) index;
            if (current[i] != null) {
                outputWord(words, newStrBldr, current[i], x);
            }
        }
        return words;
    }

//    /**
//     * The toString specification is as follows:
//     * For each word, in alphabetical order:
//     * <word>\n
//     */
//    @Override
//    public String toString() {
//        StringBuilder currentSB = new StringBuilder();
//        Node[] current = getRoot();
//        for (int i = 0; i < ALPHABETSIZE; i++) {
//            int index = 'a' + i;
//            char c = (char) index;
//            if (current[i] != null) {
//                outputWord(currentSB, current[i], c);
//            }
//            currentSB.setLength(0);
//        }
//        String output = sb.toString();
//        sb.setLength(0);
//        return output;
//    }

    @Override
    public int hashCode() {
        return ((nodeCount * 13) + (wordCount * 31));
    }

    public boolean matchNodes(Node n1, Node n2) {
        if (n1.getValue() != n2.getValue()) {
            return false;
        }
        Node[] current1 = n1.getNodes();
        Node[] current2 = n2.getNodes();
        for (int i = 0; i < ALPHABETSIZE; i++) {
            if (current1[i] != null && current2[i] != null) {
                return matchNodes(current1[i], current2[i]);
            }
            else if (current1[i] == null && current2[i] == null) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Trie t = (Trie) o;
        if ((t.getWordCount() == getWordCount()) && (t.getNodeCount() == getNodeCount())) {
            Node[] current1 = getRoot();
            Node[] current2 = t.getRoot();
            for (int i = 0; i < ALPHABETSIZE; i++) {
                if (current1[i] != null && current2[i] != null) {
                    if (!matchNodes(current1[i], current2[i])) {
                        return false;
                    }
                }
                else if (current1[i] == null && current2[i] == null){
                    continue;
                }
                else {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public class Node {
        private int frequency;
        private boolean isComplete;
        private Node[] nodes;

        public Node() {
            this.frequency = 0;
            nodes = new Node[ALPHABETSIZE];
            this.isComplete = false;
        }

        public int getValue() {
            return frequency;
        }

        public void incrValue() {
            frequency++;
        }

        public Node getNode(char c) {
            int index = c - 'a';
            if (index < ALPHABETSIZE && index >= 0) {
                return nodes[index];
            } else {
                return null;
            }
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean completeness) {
            this.isComplete = completeness;
        }

        public Node[] getNodes() {
            return nodes;
        }
    }
}
