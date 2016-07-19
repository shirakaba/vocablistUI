package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    private final List<VocabListRowCumulativeMapped> list;
    private final List<Tier> quizA;
    private final List<Tier> quizB;
    private final List<Tier> quizC;

    /**
     * Just includes untieredList.
     * @param untieredList
     * @param makeQuiz
     */
    public Test6Model(List<VocabListRowCumulativeMapped> untieredList, boolean makeQuiz) {
        this.list = untieredList;
        if(makeQuiz){
            TieredSingleDefVRCMList tierHolder = new TieredSingleDefVRCMList(untieredList);

            TierGenerator tierOne = new TierGenerator(tierHolder.getTierOne(), "One");
            TierGenerator tierTwo = new TierGenerator(tierHolder.getTierTwo(), "Two");
            TierGenerator tierThree = new TierGenerator(tierHolder.getTierThree(), "Three");
            TierGenerator tierFour = new TierGenerator(tierHolder.getTierFour(), "Four");

            quizA = new ArrayList<>(
                    Arrays.asList(tierOne.getTierA(), tierTwo.getTierA(), tierThree.getTierA(), tierFour.getTierA())
            );

            quizB = new ArrayList<>(
                    Arrays.asList(tierOne.getTierB(), tierTwo.getTierB(), tierThree.getTierB(), tierFour.getTierB())
            );

            quizC = new ArrayList<>(
                    Arrays.asList(tierOne.getTierC(), tierTwo.getTierC(), tierThree.getTierC(), tierFour.getTierC())
            );
        }
        else {
            quizA = null;
            quizB = null;
            quizC = null;
        }
    }

    /**
     * Includes quizA.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
        quizA = null;
        quizB = null;
        quizC = null;
    }

    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }

    public List<Tier> getQuizA() {
        return quizA;
    }

    public List<Tier> getQuizB() {
        return quizB;
    }

    public List<Tier> getQuizC() {
        return quizC;
    }
}
