package uk.co.birchlabs;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.birchlabs.QuizRowFactory.*;

/**
 * Created by jamiebirch on 13/07/2016.
 */
public class Quiz {
    private final List<QuizRow> kanjiQuiz;
    private final List<QuizRow> pronQuiz;
    private final List<QuizRow> defQuiz;

    private static final Integer MAX_QUESTIONS= 4;
    private static final Integer MAX_TIERS= 4;

    public Quiz(){
        kanjiQuiz = new ArrayList<>();
        pronQuiz = new ArrayList<>();
        defQuiz = new ArrayList<>();
    }

    public Quiz(List<VocabListRowCumulativeMapped> untieredList) {
        TieredSingleDefVRCMList tierHolder = new TieredSingleDefVRCMList(untieredList);

        List<VocabListRowCumulativeMapped> t1 = tierHolder.getTierOne(),
                t2 = tierHolder.getTierTwo(),
                t3 = tierHolder.getTierThree(),
                t4 = tierHolder.getTierFour();

        List<VocabListRowCumulativeMapped> rowsWithKanjiInDefs = new ArrayList<>();
        rowsWithKanjiInDefs.addAll(findRowsWithKanjiInDefs(t1));
        rowsWithKanjiInDefs.addAll(findRowsWithKanjiInDefs(t2));
        rowsWithKanjiInDefs.addAll(findRowsWithKanjiInDefs(t3));
        rowsWithKanjiInDefs.addAll(findRowsWithKanjiInDefs(t4));
        kanjiQuiz = VLRCMListToQuizList(rowsWithKanjiInDefs, Type.kanji);

        List<VocabListRowCumulativeMapped> rowsNotYetUsed = new ArrayList<>();
        rowsNotYetUsed.addAll(t1.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsNotYetUsed.addAll(t2.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsNotYetUsed.addAll(t3.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsNotYetUsed.addAll(t4.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        pronQuiz = VLRCMListToQuizList(rowsNotYetUsed, Type.pron);

        List<VocabListRowCumulativeMapped> rowsStillNotYetUsed = new ArrayList<>();
        rowsStillNotYetUsed.addAll(t1.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)
                && !rowsNotYetUsed.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsStillNotYetUsed.addAll(t2.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)
                && !rowsNotYetUsed.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsStillNotYetUsed.addAll(t3.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)
                && !rowsNotYetUsed.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        rowsStillNotYetUsed.addAll(t4.stream().filter(row -> !rowsWithKanjiInDefs.contains(row)
                && !rowsNotYetUsed.contains(row)).limit(MAX_QUESTIONS).collect(Collectors.toList()));
        defQuiz = VLRCMListToQuizList(rowsStillNotYetUsed, Type.def);
    }


    private List<VocabListRowCumulativeMapped> findRowsWithKanjiInDefs(List<VocabListRowCumulativeMapped> rows){
        return rows
                .stream()
                .filter(row -> EntryReadout.defHasKanji(row.getDefs().get(0)))
                .limit(MAX_QUESTIONS)
                .collect(Collectors.toList());
    }

    private List<QuizRow> VLRCMListToQuizList(List<VocabListRowCumulativeMapped> list, Type type){
        return list
                .stream()
                .map(row -> QuizRowFactory.getQuizRow(type, row.getBf(), row.getDefs().get(0)))
                .limit(MAX_QUESTIONS * MAX_TIERS)
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
