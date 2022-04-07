package com.example.project_tu.sections;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_tu.CourActivity;
import com.example.project_tu.quiz.QuizActivity;
import com.example.project_tu.date.DateActivity;

import java.util.ArrayList;

public class SectionList extends AppCompatActivity {
    ArrayList<Section> listSection;
    public SectionList(){
        listSection=new ArrayList<Section>();
    }
    public int size(){
        return listSection.size();
    }
    public Section get(int pos){
        return listSection.get(pos);
    }
    public void construireListeMenu(){
                listSection.add(new Section("Cours", CourActivity.class));
                listSection.add(new Section("Traduction d'une Date", DateActivity.class));
                listSection.add(new Section("Quiz", QuizActivity.class));
                listSection.add(new Section("Anagramme Jeu!", com.example.project_tu.word.WordActivity.class));

    }
    public void construireListeCours(){
                listSection.add(new Section("Participle Clauses", com.example.project_tu.cours.CoursClause.class));
                listSection.add(new Section("Temps", com.example.project_tu.cours.CoursTenses.class));
                listSection.add(new Section("Vocabulaire", com.example.project_tu.cours.CoursEnglishWord.class));


    }
}
