package unal.todosalau.frasesalvuelo.repositorios;

import java.util.ArrayList;

public class PhrasesRepository {
    private static PhrasesRepository sInstance;

    private ArrayList<String> mPhrasesList;

    private PhrasesRepository() {
        mPhrasesList = new ArrayList<>();
    }

    public static PhrasesRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PhrasesRepository();
        }

        return sInstance;
    }

    public ArrayList<String> getPhrasesList() {
        return mPhrasesList;
    }

    public void addPhrase(String phrase) {
        mPhrasesList.add(phrase);
    }

    public void clearPhrases() {
        mPhrasesList.clear();
    }
}
