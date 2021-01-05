package shared;

import datetime.Date;
import datetime.FullDateTime;
import datetime.QTime;

import java.util.List;

public class Query {
    private int id;
    private List<String> queryKeywords;
    private FullDateTime dateTime;
    private List<String> clickedDocs;

    public Query(int id, List<String> queryKeywords, FullDateTime dateTime) {
        this.id = id;
        this.queryKeywords = queryKeywords;
        this.dateTime = dateTime;
    }

    public Query(int id, List<String> queryKeywords, FullDateTime dateTime, List<String> clickedDocs) {
        this.id = id;
        this.queryKeywords = queryKeywords;
        this.dateTime = dateTime;
        this.clickedDocs = clickedDocs;
    }

    public Date getDate() {
        return dateTime.getDate();
    }

    public int getId() {
        return id;
    }

    public String getLastWord() {
        return queryKeywords.get(queryKeywords.size() - 1);
    }

    public String getWordAt(int index) {
        return queryKeywords.get(index);
    }

    public List<String> getQueryKeywords() {
        return queryKeywords;
    }

    public QTime getTime() {
        return dateTime.getTime();
    }

    public void setDate(Date date) {
        this.dateTime.setDate(date);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQueryKeywords(List<String> queryKeywords) {
        this.queryKeywords = queryKeywords;
    }

    public void setTime(QTime time) {
        this.dateTime.setTime(time);
    }

    public void setClickedDocs(List<String> clickedDocs) {
        this.clickedDocs = clickedDocs;
    }

    public List<String> getClickedDocs() {
        return clickedDocs;
    }

    public boolean contains(Query q) {
        if (toString().contains(q.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < queryKeywords.size(); i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(queryKeywords.get(i));
        }
        return sb.toString();
    }

    public int getQueryLength() {
        return queryKeywords.size();
    }
}
