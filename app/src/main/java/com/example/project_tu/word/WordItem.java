package com.example.project_tu.word;
//game will be from word 4 words -> 6 words
//
public class WordItem {
    private String key1, key2, key3, key4, key5, key6, key7,textAnswer,textQuestion;
    private int size;
    public WordItem(String key1, String key2, String key3, String key4, String key5, String key6, String key7, String textAnswer, String textQuestion) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
        this.key6 = key6;
        this.key7=key7;
        this.textAnswer = textAnswer;
        this.textQuestion=textQuestion;
        this.size = 7;
    }
    public WordItem(String key1, String key2, String key3, String key4, String key5, String key6, String textAnswer, String textQuestion) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
        this.key6 = key6;
        this.textAnswer = textAnswer;
        this.textQuestion=textQuestion;
        this.size = 6;
    }

    public WordItem( String key1, String key2, String key3, String key4, String key5, String textAnswer, String textQuestion) {
        this.size=5;
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
        this.textQuestion=textQuestion;
        this.textAnswer = textAnswer;
    }
    public String getKey(int num){
        switch (num){
            case 0:
                return key1;
            case 1:
                return key2;
            case 2:
                return key3;
            case 3:
                return key4;
            case 4:
                return key5;
            case 5:
                return key6;
            case 6:
                return key7;
            default:
                return "undefined";
        }
    }
    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }

    public String getKey3() {
        return key3;
    }

    public String getKey4() {
        return key4;
    }

    public String getKey5() {
        return key5;
    }

    public String getKey6() {
        return key6;
    }

    public String getKey7() {
        return key7;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public int getSize() {
        return size;
    }

    public String getTextAnswer() {
        return textAnswer;
    }
}
