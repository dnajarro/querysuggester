package textprocessing;

import shared.*;

import java.util.ArrayList;
import java.util.List;

public class ParserResults {
    private FreqRecord freqRecord;
    private ModRecord modRecord;

    public ParserResults(FreqRecord freqRecord, ModRecord modRecord) {
        this.freqRecord = freqRecord;
        this.modRecord = modRecord;
    }

    public List<Query> getSQAsList() {
        List<Query> queryList = new ArrayList<>(modRecord.getAllSQ());
        return queryList;
    }

    public FreqRecord getFreqRecord() {
        return freqRecord;
    }

    public ModRecord getModRecord() {
        return modRecord;
    }

    public void setFreqRecord(FreqRecord freqRecord) {
        this.freqRecord = freqRecord;
    }

    public void setModRecord(ModRecord modRecord) {
        this.modRecord = modRecord;
    }

    public Mod getMod(String q, String sq) {
        return modRecord.getMod(q, sq);
    }

    public Query getQuery(String q) {
        Freq freq = freqRecord.getFreq(q);
        if (freq != null) {
            return freq.getSugQuery();
        }
        return modRecord.getQuery(q);
    }

}
