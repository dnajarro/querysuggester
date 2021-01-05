package sugrank;

import textprocessing.ParserResults;
import shared.FreqRecord;
import shared.ModRecord;
import shared.Query;

public class Ranker {
    private WCF_App wcf_app;
    private ModRecord modRecord;
    private FreqRecord freqRecord;
    private PorterStemmer stemmer;


    public Ranker(ParserResults results) {
        this.wcf_app = new WCF_App();
        this.modRecord = results.getModRecord();
        this.freqRecord = results.getFreqRecord();
        this.stemmer = new PorterStemmer();
    }

    public double rank(Query q, Query sq) {
        double freq = (double) freqRecord.getFreq(sq);
        double mod = (double) modRecord.getMod(q, sq);
        if (freq == -1 || mod == -1) {
            return -1;
        }
        double normalizedFreq = freq / freqRecord.getMostFreq();
        double normalizedMod = mod / modRecord.getMostMod();
        String qLastWordStem = stemmer.stem(q.getLastWord());
        String sqNextWordStem = stemmer.stem(sq.getWordAt(q.getQueryLength()));
        double wcf = 0.5;//wcf_app.getWCF(qLastWordStem, sqNextWordStem);
        if (wcf == -1) {
            wcf = 0;
        }
        double minVal = Math.min(Math.min(freq, mod), wcf);
        return (freq + wcf + mod)/(1 - minVal);
    }
}
