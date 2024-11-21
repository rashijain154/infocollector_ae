package com.avirantenterprises.infocollector.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Form {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

}
