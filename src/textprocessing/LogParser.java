package textprocessing;

import datetime.Date;
import datetime.FullDateTime;
import datetime.QTime;
import shared.FreqRecord;
import shared.ModRecord;
import shared.Query;
import shared.StopWords;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LogParser {
    private FreqRecord freqRecord;
    private ModRecord modRecord;

    public LogParser() {
        freqRecord = new FreqRecord();
        modRecord = new ModRecord();
    }

    public ParserResults getResults() {
        return new ParserResults(freqRecord, modRecord);
    }

    public void parse(String filename) {
        StopWords stopWords = new StopWords();
        try {
            Scanner scanner = new Scanner(new File(filename));
            String line;
            int lineNum = 1;
            int curId = -1;
            int prevId = -1;
            Query prevQuery = null;
            FullDateTime prevDateTime = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] segments = line.split("\t");   // split the different segments of each line
                if (lineNum != 1) {
                    String query = segments[1];
                    List<String> queryKeywords = Arrays.asList(query.split("[ \t]"));   // get the query keywords
                    queryKeywords = removeLeadingStopWords(queryKeywords, stopWords);

                    String[] dateTime = segments[2].split(" ");
                    datetime.Date date = parseDate(dateTime[0]); // get date
                    QTime time = parseTime(dateTime[1]); // get time
                    int newId = Integer.parseInt(segments[0]);  // get the ID
                    if (curId == -1 || curId != newId) {    // new session
                        curId = newId;
                    }
                    FullDateTime curDateTime = new FullDateTime(date, time);
                    Query q = new Query(curId, queryKeywords, curDateTime);
                    if (segments.length > 3) {  // extract clicked documents
                        List<String> clickedDocs = Arrays.asList(segments[3].split(" "));
                        q.setClickedDocs(clickedDocs);
                    }
                    storeFreq(q);
                    if (hasPrevQuery(prevDateTime, prevId, prevQuery)) {
                        if (isMod(prevQuery, q, prevId, curId, curDateTime, prevDateTime)) {
                            storeMod(prevQuery, q);
                        }
                    }
                    prevQuery = q;
                    prevDateTime = curDateTime;
                    prevId = curId;
                }
                lineNum++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public boolean hasPrevQuery(FullDateTime prevDateTime, int prevId, Query prevQuery) {
        if (prevDateTime != null) {
            if (prevId != -1) {
                return prevQuery != null;
            }
        }
        return false;
    }

    public boolean isMod(Query query, Query sugQuery, int prevId, int curId, FullDateTime prevDateTime, FullDateTime curDateTime) {
        if (curId == prevId) {
            if (isSameSession(prevDateTime, curDateTime)) {
                if (sugQuery.contains(query)) {
                    return sugQuery.getQueryKeywords().size() - query.getQueryKeywords().size() >= 1;
                }
            }
        }
        return false;
    }

    public boolean isSameSession(FullDateTime prevDateTime, FullDateTime curDateTime) {
        if (curDateTime != null && prevDateTime != null) {
            return prevDateTime.isWithin10Min(curDateTime.getDate(), curDateTime.getTime());
        }
        return false;
    }

    public void storeFreq(Query sq) {
        freqRecord.add(sq);
    }

    public void storeMod(Query q, Query sq) {
        modRecord.add(q, sq);
    }

    public List<String> removeLeadingStopWords(List<String> tokens, StopWords stopWords) {
        List<String> validTokens = new ArrayList<>();
        int i = 0;
        while (i < tokens.size() && stopWords.contains(tokens.get(i))) {
            i++;
        }
        if (i == 0) {   // no leading stopwords
            return tokens;
        }
        while (i < tokens.size()) {
            validTokens.add(tokens.get(i));
            i++;
        }
        return validTokens;
    }

    public datetime.Date parseDate(String dateStr) {
        String[] dateVals = dateStr.split("-");
        return new Date(Integer.parseInt(dateVals[0]), Integer.parseInt(dateVals[1]), Integer.parseInt(dateVals[2]));
    }

    public QTime parseTime(String timeStr) {
        String[] timeVals = timeStr.split(":");
        return new QTime(Integer.parseInt(timeVals[0]), Integer.parseInt(timeVals[1]), Integer.parseInt(timeVals[2]));
    }
}
