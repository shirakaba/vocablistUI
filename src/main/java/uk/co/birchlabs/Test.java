package uk.co.birchlabs;

import java.util.List;

/**
 * Created by jamiebirch on 15/07/2016.
 */
public class Test {
    private final List<Question> questions;
    private final String testType;
    private final String eleType;
    private final String tierAlpha;
    private final String[] allowedTypes;

    public Test(List<Question> questions, String testType, String tierAlpha) {
        this.questions = questions;
        this.testType = testType;
        this.tierAlpha = tierAlpha;
        eleType = testType + tierAlpha;
        allowedTypes = new String[]{eleType};
    }

    public List<Question> getQus() {
        return questions;
    }

    public String getTestType() {
        return testType;
    }

    public String getTierAlpha() {
        return tierAlpha;
    }

    public String getEleType() {
        return eleType;
    }

    public String[] getAllowedTypes() {
        return allowedTypes;
    }
}
