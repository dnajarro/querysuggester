package textprocessing;

public class Poller implements Runnable {
    private StringBuilder stringBuilder;
    private int curLength;
    private IUserInput mainThread;
    private boolean exit;

    public Poller(IUserInput mainThread) {
        stringBuilder = new StringBuilder();
        this.mainThread = mainThread;
        this.curLength = 0;
        this.exit = false;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (!exit) {
            String s = mainThread.getNextLine();
            if (!s.isEmpty()) {
                if (s.equals("END")) {
                    mainThread.closeScanner();
                    return;
                }
                stringBuilder.append(s);
                mainThread.printSuggestions(stringBuilder.toString());
                clearLine();
            }
        }
    }

    public boolean hasDelimiter(String s) {
        if (!s.contains(" ") && !s.contains("\t") && !s.contains("\n")) {
            return false;
        }
        return true;
    }

    public int lastIndexOfDelimiter() {
        String curStr = stringBuilder.toString();
        return Math.max(Math.max(curStr.lastIndexOf(' '), curStr.lastIndexOf('\t')), curStr.lastIndexOf('\n'));
    }

    public void clearLine() {
        stringBuilder.setLength(0);
    }

    public void stop() {
        exit = true;
    }
}
