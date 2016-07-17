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
public class TierGenerator {

    private final Tier tierA;
    private final Tier tierB;

    public static final Integer MAX_QUESTIONS= 8;


    public TierGenerator(List<VocabListRowCumulativeMapped> tieredList, String tierAlpha) {
        List<VocabListRowCumulativeMapped> rowsAlreadyClaimed = new ArrayList<>();
        List<Question> unpartitioned;
        List<List<Question>> partitioned;

        List<VocabListRowCumulativeMapped> rowsForKanji = findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed, MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForKanji, kanji);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<Question> kanjiQuizA = partitioned.get(0);
        List<Question> kanjiQuizB = partitioned.get(1);
        rowsAlreadyClaimed.addAll(rowsForKanji);

        List<VocabListRowCumulativeMapped> rowsForPron = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForPron, pron);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<Question> pronQuizA = partitioned.get(0);
        List<Question> pronQuizB = partitioned.get(1);
        rowsAlreadyClaimed.addAll(rowsForPron);

        List<VocabListRowCumulativeMapped> rowsForDef = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForDef, def);
        partitioned = Lists.partition(unpartitioned, unpartitioned.size() / 2);
        List<Question> defQuizA = partitioned.get(0);
        List<Question> defQuizB = partitioned.get(1);

        tierA = new Tier(kanjiQuizA, pronQuizA, defQuizA, tierAlpha);
        tierB = new Tier(kanjiQuizB, pronQuizB, defQuizB, tierAlpha);
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


    private List<Question> VLRCMListToQuizList(List<VocabListRowCumulativeMapped> list, Mode mode){
        return list
                .stream()
                .map(row -> getQuizRow(mode, row))
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }

    private List<VocabListRowCumulativeMapped> withoutPNouns(List<VocabListRowCumulativeMapped> rows, Integer limit){
        return rows.stream().filter(row -> !row.isPNoun()).limit(limit).collect(Collectors.toList());
    }

    public Tier getTierA() {
        return tierA;
    }

    public Tier getTierB() {
        return tierB;
    }
}
