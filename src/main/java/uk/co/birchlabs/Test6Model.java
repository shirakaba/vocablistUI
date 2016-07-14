package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    List<VocabListRowCumulativeMapped> list;
    GrandQuiz quiz;
//    private static final Integer MAX_TOKENS= 10;


    /**
     * Just includes list.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list, boolean makeQuiz) {
        this.list = list;
        if(makeQuiz){
            this.quiz = new GrandQuiz(list);
        }
        else this.quiz = new GrandQuiz();
    }

    /**
     * Includes quiz.
     * @param list
     */
    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
        this.quiz = new GrandQuiz();
    }

    public List<VocabListRowCumulativeMapped> getList()
    {
        return list;
    }

    public GrandQuiz getQuiz() {
        return quiz;
    }
}
