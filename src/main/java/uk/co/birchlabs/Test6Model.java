package uk.co.birchlabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    private final List<VocabListRowCumulativeMapped> list;
    private final List<SingleTierQuiz> quizATiers;
    private final List<SingleTierQuiz> quizBTiers;

    /**
     * Just includes untieredList.
     * @param untieredList
     * @param makeQuiz
     */
    public Test6Model(List<VocabListRowCumulativeMapped> untieredList, boolean makeQuiz) {
        this.list = untieredList;
        if(makeQuiz){
            TieredSingleDefVRCMList tierHolder = new TieredSingleDefVRCMList(untieredList);

            SingleTierQuizGenerator tierOne = new SingleTierQuizGenerator(tierHolder.getTierOne(), "One");
            SingleTierQuizGenerator tierTwo = new SingleTierQuizGenerator(tierHolder.getTierTwo(), "Two");
            SingleTierQuizGenerator tierThree = new SingleTierQuizGenerator(tierHolder.getTierThree(), "Three");
            SingleTierQuizGenerator tierFour = new SingleTierQuizGenerator(tierHolder.getTierFour(), "Four");

            quizATiers = new ArrayList<>(
                    Arrays.asList(tierOne.getQuizA(), tierTwo.getQuizA(), tierThree.getQuizA(), tierFour.getQuizA())
            );

            quizBTiers = new ArrayList<>(
                    Arrays.asList(tierOne.getQuizB(), tierTwo.getQuizB(), tierThree.getQuizB(), tierFour.getQuizB())
            );
        }
        else {
            quizATiers = null;
            quizBTiers = null;
        }
    }

    /**
     * Includes quizA.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
        quizATiers = null;
        quizBTiers = null;
    }

    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }

    public List<SingleTierQuiz> getQuizATiers() {
        return quizATiers;
    }

    public List<SingleTierQuiz> getQuizBTiers() {
        return quizBTiers;
    }
}
