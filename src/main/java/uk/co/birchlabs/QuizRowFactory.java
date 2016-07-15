package uk.co.birchlabs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class QuizRowFactory {
    public enum Mode {
        def,
        pron,
        kanji
    }

    public static QuizRow getQuizRow(Mode mode, VocabListRowCumulativeMapped row, String type){
        switch (mode){
            case def:
                return new DefQuizRow(row, type);
            case pron:
                return new PronQuizRow(row, type);
            case kanji:
                return new KanjiQuizRow(row, type);
            default:
                throw new NotImplementedException();
        }
    }
}
