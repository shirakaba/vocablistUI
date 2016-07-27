package uk.co.birchlabs;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final Tier tierC;

    public static final Integer MAX_QUESTIONS= 12;
    public static final Integer QUIZZES_NEEDED = 3;


    public TierGenerator(List<VocabListRowCumulativeMapped> tieredList, String tierAlpha) {
        List<VocabListRowCumulativeMapped> rowsAlreadyClaimed = new ArrayList<>();
        List<Question> unpartitioned;
        List<List<Question>> partitioned;

        List<VocabListRowCumulativeMapped> rowsForKanji = findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed, MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForKanji, kanji);
        if(unpartitioned.size() < QUIZZES_NEEDED) partitioned = Arrays.asList(unpartitioned, new ArrayList<>(), new ArrayList<>());
        else partitioned = Lists.partition(unpartitioned, unpartitioned.size() / QUIZZES_NEEDED);
        List<Question> kanjiQuizA = getPartitionIfNonEmpty(partitioned, 0);
        List<Question> kanjiQuizB = getPartitionIfNonEmpty(partitioned, 1);
        List<Question> kanjiQuizC = getPartitionIfNonEmpty(partitioned, 2);
        rowsAlreadyClaimed.addAll(rowsForKanji);

        List<VocabListRowCumulativeMapped> rowsForPron = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForPron, pron);
        if(unpartitioned.size() < QUIZZES_NEEDED) partitioned = Arrays.asList(unpartitioned, new ArrayList<>(), new ArrayList<>());
        else partitioned = Lists.partition(unpartitioned, unpartitioned.size() / QUIZZES_NEEDED);
        List<Question> pronQuizA = getPartitionIfNonEmpty(partitioned, 0);
        List<Question> pronQuizB = getPartitionIfNonEmpty(partitioned, 1);
        List<Question> pronQuizC = getPartitionIfNonEmpty(partitioned, 2);
        rowsAlreadyClaimed.addAll(rowsForPron);

        List<VocabListRowCumulativeMapped> rowsForDef = withoutPNouns(findRowsNotYetClaimed(tieredList, rowsAlreadyClaimed), MAX_QUESTIONS);
        unpartitioned = VLRCMListToQuizList(rowsForDef, def);
        // Got an IllegalArgumentException: null here for a very small topic - maybe stream().collect() as used in many
        // of these methods doesn't guarantee a new ArrayList<>(), sometimes returning null. Issue is that it crashes the
        // whole Spring backend.
        if(unpartitioned.size() < QUIZZES_NEEDED) partitioned = Arrays.asList(unpartitioned, new ArrayList<>(), new ArrayList<>());
        else partitioned = Lists.partition(unpartitioned, unpartitioned.size() / QUIZZES_NEEDED);
        List<Question> defQuizA = getPartitionIfNonEmpty(partitioned, 0);
        List<Question> defQuizB = getPartitionIfNonEmpty(partitioned, 1);
        List<Question> defQuizC = getPartitionIfNonEmpty(partitioned, 2);

        tierA = new Tier(kanjiQuizA, pronQuizA, defQuizA, tierAlpha);
        tierB = new Tier(kanjiQuizB, pronQuizB, defQuizB, tierAlpha);
        tierC = new Tier(kanjiQuizC, pronQuizC, defQuizC, tierAlpha);
    }

    private List<Question> getPartitionIfNonEmpty(List<List<Question>> partitioned, int index){
        if(partitioned.size() > index) return partitioned.get(index);
        else return new ArrayList<>();
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

    public Tier getTierC() {
        return tierC;
    }
}
