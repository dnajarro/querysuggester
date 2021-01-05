package textprocessing;

import shared.Query;
import sugrank.Ranker;
import sugrank.Ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuerySuggester implements IUserInput {
    private Scanner scanner;
    private Trie trie;
    private Ranker ranker;
    private ParserResults results;
    private Poller poller;

    public void suggestQueries() {
        LogParser logParser = new LogParser();
        System.out.println("Parsing query logs...");
        logParser.parse("data/Clean-Data-01.txt");
        System.out.println("Clean-Data-01.txt complete.");
        logParser.parse("data/Clean-Data-02.txt");
        System.out.println("Clean-Data-02.txt complete.");
        logParser.parse("data/Clean-Data-03.txt");
        System.out.println("Clean-Data-03.txt complete.");
        logParser.parse("data/Clean-Data-04.txt");
        System.out.println("Clean-Data-04.txt complete.");
        logParser.parse("data/Clean-Data-05.txt");
        System.out.println("Clean-Data-05.txt complete.");

        results = logParser.getResults();
        trie = new Trie();
        System.out.println("Populating trie.");
        trie.add(results.getSQAsList());
        ranker = new Ranker(results);
        System.out.println("Ready for input. Press ENTER to get query suggestions. Type END to quit.");

        poller = new Poller(this);
        Thread polling = new Thread(poller);
        scanner = new Scanner(System.in);
        polling.start();
//        while (polling.isAlive()) {
//
//        }
    }

    @Override
    public void printSuggestions(String query) {
        List<String> suggestions = trie.findSuggestions(query);
        List<Ranking> ranking = new ArrayList<>();
        if (suggestions != null) {
            for (String sug : suggestions) {
                Query q = results.getQuery(query);
                Query sq = results.getQuery(sug);
                if (q != null && sq != null) {
                    double score = ranker.rank(q, sq);
                    if (score >= 0) {   // score of -1 means no matching queries found
                        ranking.add(new Ranking(q.toString(), sq.toString(), score));
                    }
                }
            }
        }
        if (!ranking.isEmpty()) {
            Collections.sort(ranking);
            int resultsSize = 8;
            for (int i = 0; i < ranking.size(); i++) {
                if (i == resultsSize) {
                    break;
                }
                System.out.println((i + 1) + ": " + ranking.get(i).getSugQuery());
            }
        } else {
            System.out.println("No query suggestions.");
        }
    }

    @Override
    public String getNextLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    @Override
    public void closeScanner() {
        scanner.close();
        poller.stop();
    }
}
