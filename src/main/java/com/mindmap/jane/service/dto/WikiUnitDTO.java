package com.mindmap.jane.service.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the WikiUnit entity.
 */
public class WikiUnitDTO implements Serializable {

    private String id;

    private String name;

    private SentenceDTO sentenceName;

    private String topic;

    private List<MeaningDTO> meanings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public SentenceDTO getSentenceName() {
        return sentenceName;
    }

    public void setSentenceName(SentenceDTO sentenceName) {
        this.sentenceName = sentenceName;
    }

    public List<MeaningDTO> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<MeaningDTO> meanings) {
        this.meanings = meanings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WikiUnitDTO that = (WikiUnitDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WikiUnitDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", sentenceName=" + sentenceName +
            ", topic='" + topic + '\'' +
            ", meanings=" + meanings +
            '}';
    }
}
