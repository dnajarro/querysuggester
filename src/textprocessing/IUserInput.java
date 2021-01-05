package textprocessing;

public interface IUserInput {
    void printSuggestions(String query);
    String getNextLine();
    void closeScanner();
}
