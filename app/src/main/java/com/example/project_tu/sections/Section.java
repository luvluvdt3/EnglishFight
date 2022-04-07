package com.example.project_tu.sections;

public class Section {
    private String nom;
    private Class linkToPage;

    public Section(String nom, Class linkToPage) {
        this.nom = nom;
        this.linkToPage = linkToPage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Class getLinkToPage() {
        return linkToPage;
    }

    public void setLinkToPage(Class linkToPage) {
        this.linkToPage = linkToPage;
    }
}
