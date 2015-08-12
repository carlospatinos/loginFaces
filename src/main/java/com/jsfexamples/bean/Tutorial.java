package com.jsfexamples.bean;

import java.io.Serializable;

public class Tutorial implements Serializable {
	
	private static final long serialVersionUID = 8325446322995393724L;
	private String tutorialName;
    private String tutorialInstructor;
    private String tutorialPeriod;
    private String tutorialPrice;
    private String tutorialStartDate;
    private String tutorialEndDate;
 
    public String getTutorialName() {
        return tutorialName;
    }
    public void setTutorialName(String tutorialName) {
        this.tutorialName = tutorialName;
    }
    public String getTutorialInstructor() {
        return tutorialInstructor;
    }
    public void setTutorialInstructor(String tutorialInstructor) {
        this.tutorialInstructor = tutorialInstructor;
    }
    public String getTutorialPeriod() {
        return tutorialPeriod;
    }
    public void setTutorialPeriod(String tutorialPeriod) {
        this.tutorialPeriod = tutorialPeriod;
    }
    public String getTutorialPrice() {
        return tutorialPrice;
    }
    public void setTutorialPrice(String tutorialPrice) {
        this.tutorialPrice = tutorialPrice;
    }
    public String getTutorialStartDate() {
        return tutorialStartDate;
    }
    public void setTutorialStartDate(String tutorialStartDate) {
        this.tutorialStartDate = tutorialStartDate;
    }
    public String getTutorialEndDate() {
        return tutorialEndDate;
    }
    public void setTutorialEndDate(String tutorialEndDate) {
        this.tutorialEndDate = tutorialEndDate;
    }
}