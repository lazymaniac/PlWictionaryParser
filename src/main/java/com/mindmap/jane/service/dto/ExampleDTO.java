package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Example entity.
 */
public class ExampleDTO implements Serializable {

    private String id;

    private SentenceDTO sentence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SentenceDTO getSentence() {
        return sentence;
    }

    public void setSentence(SentenceDTO sentence) {
        this.sentence = sentence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExampleDTO exampleDTO = (ExampleDTO) o;

        return Objects.equals(id, exampleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sentence);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExampleDTO{");
        sb.append("id='").append(id).append('\'');
        sb.append(", sentence='").append(sentence).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
