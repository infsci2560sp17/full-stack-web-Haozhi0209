/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infsci2560.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author zhihao
 */
@Entity
public class Law {

    private static final long serialVersionUID = 1L;

    public enum QuestionType {
        Consult,
        Article,
        Survey,
        Unknown
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String title;
    protected String contents;
    protected String comments;
    protected QuestionType QuestionType;

    public Law() {
        this.id = Long.MAX_VALUE;
        this.title = null;
        this.contents = null;
        this.comments = null;
        this.QuestionType = QuestionType.Unknown;
    }

    public Law(Long id, String title, String contents,QuestionType QuestionType) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.QuestionType = QuestionType;
    }

    @Override
    public String toString() {
        return "[ id=" + this.id + ", title=" + this.title + ", contents=" + this.contents + ", QuestionType=" + this.QuestionType + " ]";
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * @return the name
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the name to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the QuestionType
     */
    public QuestionType getQuestionType() {
        return QuestionType;
    }

    /**
     * @param set QuestionType
     */
    public void setQuestionType(QuestionType QuestionType) {
        this.QuestionType = QuestionType;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

     /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param id the id to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    

}

