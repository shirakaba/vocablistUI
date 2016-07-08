package uk.co.birchlabs;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by jamiebirch on 22/06/2016.
 */
public class Test6Model {
    List<VocabListRowCumulativeMapped> list;

    public Test6Model(List<VocabListRowCumulativeMapped> list) {
        this.list = list;
    }

    public List<VocabListRowCumulativeMapped> getList()
    {
//        return Lists.partition(list, 100).get(0);
        return list;
    }
}
