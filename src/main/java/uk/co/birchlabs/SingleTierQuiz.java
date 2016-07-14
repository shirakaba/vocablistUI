package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.birchlabs.QuizRowFactory.*;
import static uk.co.birchlabs.QuizRowFactory.Type.*;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class SingleTierQuiz {
    private final List<QuizRow> kanjiQuiz;
    private final List<QuizRow> pronQuiz;
    private final List<QuizRow> defQuiz;

    public static final Integer MAX_QUESTIONS= 4;
    public static final Integer MAX_TIERS= 4;


    public SingleTierQuiz(List<VocabListRowCumulativeMapped> tieredList) {
        List<VocabListRowCumulativeMapped> rowsWithKanjiInDefs = findRowsWithKanjiInDefs(tieredList, MAX_QUESTIONS);
        kanjiQuiz = VLRCMListToQuizList(rowsWithKanjiInDefs, kanji, MAX_QUESTIONS * MAX_TIERS);
        List<VocabListRowCumulativeMapped> rowsAlreadyClaimed = new ArrayList<>();
        rowsAlreadyClaimed.addAll(rowsWithKanjiInDefs);

        List<VocabListRowCumulativeMapped> rowsForPron = findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed, MAX_QUESTIONS);
        pronQuiz = VLRCMListToQuizList(rowsForPron, pron, MAX_QUESTIONS * MAX_TIERS);
        rowsAlreadyClaimed.addAll(rowsForPron);

        List<VocabListRowCumulativeMapped> rowsForDef = findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed, MAX_QUESTIONS);
        defQuiz = VLRCMListToQuizList(rowsForDef, def, MAX_QUESTIONS * MAX_TIERS);
        rowsAlreadyClaimed.addAll(rowsForDef);
    }

    private List<VocabListRowCumulativeMapped> findRowsNotYetClaimed(List<VocabListRowCumulativeMapped> tieredList,
                                                                     List<VocabListRowCumulativeMapped> alreadyClaimed,
                                                                     Integer limit){
        return tieredList
                .stream()
                .filter(row -> !alreadyClaimed.contains(row))
                .limit(limit)
                .collect(Collectors.toList());
    }


    private List<VocabListRowCumulativeMapped> findRowsWithKanjiInDefs(List<VocabListRowCumulativeMapped> rows, Integer limit){
        return rows
                .stream()
                .filter(row -> row.getEntryReadouts().get(0).descHasKanji())
                .limit(limit)
                .collect(Collectors.toList());
    }


    private List<QuizRow> VLRCMListToQuizList(List<VocabListRowCumulativeMapped> list, Type type, Integer limit){
        return list
                .stream()
                .map(row -> getQuizRow(type, row))
                .limit(limit)
                .collect(Collectors.toList());
    }


    public List<QuizRow> getKanjiQuiz() {
        return kanjiQuiz;
    }

    public List<QuizRow> getPronQuiz() {
        return pronQuiz;
    }

    public List<QuizRow> getDefQuiz() {
        return defQuiz;
    }
}
