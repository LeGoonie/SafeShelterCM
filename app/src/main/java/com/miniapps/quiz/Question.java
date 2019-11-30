package com.miniapps.quiz;

import java.util.ArrayList;
import java.util.List;

import com.example.safeshelter.R;



public class Question {

    public enum Type {TEXT, RADIO, CHECK}

    private Type type;
    private String string;
    private int image;
    private String[] answers;
    private String[] correctAnswers;

    public Question(Type type, String string, int image, String[] answers, String[] correctAnswers) {
        this.type = type;
        this.string = string;
        this.image = image;
        this.answers = answers;
        this.correctAnswers = correctAnswers;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String[] getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }


    public static List<Question> getQuestions(){
        List<Question> mList = new ArrayList<>();

        mList.add(new Question(Type.TEXT,"Escreve o nome do animal",R.drawable.lion,new String[]{""},new String[]{"leão"}));
        mList.add(new Question(Type.RADIO,"Qual é o nome do animal?",R.drawable.lion,new String[]{"leão","macaco","baleia","pássaro"},new String[]{"leão"}));
        mList.add(new Question(Type.CHECK,"Escolhe o animal da imagem",R.drawable.catanddog,new String[]{"cão","macaco","leão","gato"},new String[]{"gato","cão"}));
        mList.add(new Question(Type.TEXT,"Escreve o nome do animal",R.drawable.giraffe,new String[]{""},new String[]{"girafa"}));
        mList.add(new Question(Type.RADIO,"Qual é o nome do animal?",R.drawable.dog,new String[]{"leão","macaco","girafa","cão"},new String[]{"cão"}));
        mList.add(new Question(Type.CHECK,"Escolhe os animais da imagem",R.drawable.giraffeandelephant,new String[]{"cão","girafa","leão","elefante"},new String[]{"girafa","elefante"}));
        mList.add(new Question(Type.TEXT,"Escreve o nome do animal",R.drawable.dog,new String[]{""},new String[]{"cão"}));
        mList.add(new Question(Type.RADIO,"Qual é o nome do animal?",R.drawable.elephant,new String[]{"elefante","macaco","girafa","cão"},new String[]{"elefante"}));
        mList.add(new Question(Type.CHECK,"Escolhe os animais da imagem",R.drawable.monkeyandlion,new String[]{"cão","macaco","leão","elefante"},new String[]{"macaco","leão"}));

        return mList;
    }
}