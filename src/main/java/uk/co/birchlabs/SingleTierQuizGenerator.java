package uk.co.birchlabs;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.co.birchlabs.QuizRowFactory.*;
import static uk.co.birchlabs.QuizRowFactory.Mode.*;

/**
 * Created by jamiebirch on 14/07/2016.
 */
public class SingleTierQuizGenerator {

    private final SingleTierQuiz quizA;
    private final SingleTierQuiz quizB;

    public static final Integer MAX_QUESTIONS= 8;


    public SingleTierQuizGenerator(List<VocabListRowCumulativeMapped> tieredList, String tierName) {
        List<VocabListRowCumulativeMapped> rowsAlreadyClaimed = new ArrayList<>();
        List<QuizRow> unpartitioned;
        List<List<QuizRow>> partitioned;

        List<VocabListRowCumulativeMapped> rowsForKanji = findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed, MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForKanji, kanji);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<QuizRow> kanjiQuizA = partitioned.get(0);
        List<QuizRow> kanjiQuizB = partitioned.get(1);
        rowsAlreadyClaimed.addAll(rowsForKanji);

        List<VocabListRowCumulativeMapped> rowsForPron = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        // BEWARE re-use of unpartitioned and partitioned. May affect kanjiQuizA's references.
        unpartitioned = VLRCMListToQuizList(rowsForPron, pron);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<QuizRow> pronQuizA = partitioned.get(0);
        List<QuizRow> pronQuizB = partitioned.get(1);
        rowsAlreadyClaimed.addAll(rowsForPron);

        List<VocabListRowCumulativeMapped> rowsForDef = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForDef, def);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<QuizRow> defQuizA = partitioned.get(0);
        List<QuizRow> defQuizB = partitioned.get(1);
//        rowsAlreadyClaimed.addAll(rowsForDef);

        quizA = new SingleTierQuiz(kanjiQuizA, pronQuizA, defQuizA, tierName);
        quizB = new SingleTierQuiz(kanjiQuizB, pronQuizB, defQuizB, tierName);
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

    private List<VocabListRowCumulativeMapped> findRowsNotYetClaimed(List<VocabListRowCumulativeMapped> tieredList,
                                                                     List<VocabListRowCumulativeMapped> alreadyClaimed){
        return tieredList
                .stream()
                .filter(row -> !alreadyClaimed.contains(row))
                .collect(Collectors.toList());
    }


    private List<QuizRow> VLRCMListToQuizList(List<VocabListRowCumulativeMapped> list, Mode mode){
        return list
                .stream()
                .map(row -> getQuizRow(mode, row))
                .collect(Collectors.toList());
    }

    private List<VocabListRowCumulativeMapped> withoutPNouns(List<VocabListRowCumulativeMapped> rows, Integer limit){
        return rows.stream().filter(row -> !row.isPNoun()).limit(limit).collect(Collectors.toList());
    }

    public SingleTierQuiz getQuizA() {
        return quizA;
    }

    public SingleTierQuiz getQuizB() {
        return quizB;
    }
}
