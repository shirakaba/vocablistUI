package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    private final List<VocabListRowCumulativeMapped> list;
    private final GrandQuiz quizA;
    private final GrandQuiz quizB;

    /**
     * Just includes untieredList.
     * @param untieredList
     * @param makeQuiz
     */
    public Test6Model(List<VocabListRowCumulativeMapped> untieredList, boolean makeQuiz) {
        this.list = untieredList;
        if(makeQuiz){
            TieredSingleDefVRCMList tierHolder = new TieredSingleDefVRCMList(untieredList);

            SingleTierQuizGenerator tierOne = new SingleTierQuizGenerator(tierHolder.getTierOne());
            SingleTierQuizGenerator tierTwo = new SingleTierQuizGenerator(tierHolder.getTierTwo());
            SingleTierQuizGenerator tierThree = new SingleTierQuizGenerator(tierHolder.getTierThree());
            SingleTierQuizGenerator tierFour = new SingleTierQuizGenerator(tierHolder.getTierFour());

            this.quizA = new GrandQuiz(tierOne.getQuizA(), tierTwo.getQuizA(), tierThree.getQuizA(), tierFour.getQuizA());
            this.quizB = new GrandQuiz(tierOne.getQuizB(), tierTwo.getQuizB(), tierThree.getQuizB(), tierFour.getQuizB());
        }
        else {
            this.quizA = null;
            this.quizB = null;
        }
    }

    /**
     * Includes quizA.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
        this.quizA = null;
        this.quizB = null;
    }

    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }

    public GrandQuiz getQuizA() {
        return quizA;
    }

    public GrandQuiz getQuizB() {
        return quizB;
    }
}
