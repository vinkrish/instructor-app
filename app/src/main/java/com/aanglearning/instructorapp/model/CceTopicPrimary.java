package com.aanglearning.instructorapp.model;

public class CceTopicPrimary {
    private long id;
    private String name;
    private long sectionHeadingId;
    private int evaluation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSectionHeadingId() {
        return sectionHeadingId;
    }

    public void setSectionHeadingId(long sectionHeadingId) {
        this.sectionHeadingId = sectionHeadingId;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

}
