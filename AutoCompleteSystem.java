import java.util.*;

class TrieNode {
    HashMap<Character, TrieNode> children;
    HashMap<String, Integer> sentences;
    public TrieNode() {
        children = new HashMap<>();
        sentences = new HashMap<>();
    }
}

/*
n = length of sentences
k = avg length of each sentence
m = count of times input is called
 */
public class AutoCompleteSystem {
    TrieNode root;
    TrieNode currNode;
    TrieNode dead;
    StringBuilder currSentence;

    public AutoCompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        for (int i = 0; i < sentences.length; i++) { //O(n*k) T.C
            addToTrie(sentences[i], times[i]);
        }

        currSentence = new StringBuilder();
        currNode = root;
        dead = new TrieNode();
    }

    public List<String> input(char c) { //O(m*(n+(m/k)) T.C, O(k*(n*k+m)) S.C
        if (c == '#') {
            addToTrie(currSentence.toString(), 1);
            currSentence.setLength(0);
            currNode = root;
            return new ArrayList<>();
        }

        currSentence.append(c);
        if (!currNode.children.containsKey(c)) {
            currNode = dead;
            return new ArrayList<>();
        }

        currNode = currNode.children.get(c);
        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> {
            int hotA = currNode.sentences.get(a);
            int hotB = currNode.sentences.get(b);
            if (hotA == hotB) {
                return b.compareTo(a);
            }

            return hotA - hotB;
        });

        for (String sentence: currNode.sentences.keySet()) {
            heap.add(sentence);
            if (heap.size() > 3) {
                heap.remove();
            }
        }

        List<String> ans = new ArrayList<>();
        while (!heap.isEmpty()) {
            ans.add(heap.remove());
        }

        Collections.reverse(ans);
        return ans;
    }

    private void addToTrie(String sentence, int count) {
        TrieNode node = root;
        for (char c: sentence.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }

            node = node.children.get(c);
            node.sentences.put(sentence, node.sentences.getOrDefault(sentence, 0) + count);
        }
    }

    public static void main(String[] args) {
        String[] sentences = {"India", "Indica", "Indium", "Ironman", "Iron", "Irony"};
        int[] times = {6, 2, 3, 5, 1, 4};
        AutoCompleteSystem autoCompleteSystem = new AutoCompleteSystem(sentences, times);
        System.out.println("Input: I " + autoCompleteSystem.input('I'));
        System.out.println("Input: n " + autoCompleteSystem.input('n'));
        System.out.println("Input: r " + autoCompleteSystem.input('r'));
        System.out.println("Input: d " + autoCompleteSystem.input('d'));
        System.out.println("Input: # " + autoCompleteSystem.input('#'));
        System.out.println("Input: I " + autoCompleteSystem.input('I'));
        System.out.println("Input: n " + autoCompleteSystem.input('r'));
        System.out.println("Input: # " + autoCompleteSystem.input('#'));
        System.out.println("Input: I " + autoCompleteSystem.input('I'));
    }
}