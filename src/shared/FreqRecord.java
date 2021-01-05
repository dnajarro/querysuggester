package shared;

import java.util.HashMap;
import java.util.Map;

public class FreqRecord {
    private Map<String, Freq> freqs;
    private int mostFreq;

    public FreqRecord() {
        freqs = new HashMap<>();
        mostFreq = 0;
    }

    public int getMostFreq() {
        return mostFreq;
    }

    public void add(Query sq) {
        String key = sq.toString();
        if (freqs.containsKey(key)) {
            int freq = freqs.get(key).getCount() + 1;
            if (freq > mostFreq) {
                mostFreq = freq;
            }
            Freq f = freqs.get(key);
            f.setCount(freq);
            freqs.put(key, f);
        } else {
            freqs.put(key, new Freq(sq));
        }
    }

    public Freq getFreq(String sq) { return freqs.get(sq); }

    public int getFreq(Query sq) {
        Freq f = freqs.get(sq.toString());
        if (f != null) {
            return f.getCount();
        }
        return -1;
    }
 }
