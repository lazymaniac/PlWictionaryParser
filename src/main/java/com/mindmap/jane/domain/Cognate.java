package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.CognateNounTypesQualifier;
import com.mindmap.jane.domain.enumeration.CognatePartOfSpeechQualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Cognate. Template: {{pokrewne}}
 */
@Document(collection = "cognate")
public class Cognate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("link")
    private Link link;

    private List<String> cognateQualifiers;

    // If cognate is noun then information about type should be provided.
    @Field("gender_qualifier")
    private CognateNounTypesQualifier nounTypesQualifier;

    private CognatePartOfSpeechQualifier partOfSpeechQualifier;

    public Cognate(Link link, CognateNounTypesQualifier nounTypesQualifier, CognatePartOfSpeechQualifier partOfSpeechQualifier, List<String> cognateQualifiers) {
        this.link = link;
        this.nounTypesQualifier = nounTypesQualifier;
        this.partOfSpeechQualifier = partOfSpeechQualifier;
        this.cognateQualifiers = cognateQualifiers;
    }

    public Cognate(Link link) {
        this.link = link;
    }

    public Cognate() {
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Cognate link(Link link) {
        this.link = link;
        return this;
    }

    public CognateNounTypesQualifier getNounTypesQualifier() {
        return nounTypesQualifier;
    }

    public Cognate qualifier(CognateNounTypesQualifier genderQualifier) {
        this.nounTypesQualifier = genderQualifier;
        return this;
    }

    public void setNounTypesQualifier(CognateNounTypesQualifier nounTypesQualifier) {
        this.nounTypesQualifier = nounTypesQualifier;
    }

    public List<String> getCognateQualifiers() {
        return cognateQualifiers;
    }

    public void setCognateQualifiers(List<String> cognateQualifiers) {
        this.cognateQualifiers = cognateQualifiers;
    }

    public CognatePartOfSpeechQualifier getPartOfSpeechQualifier() {
        return partOfSpeechQualifier;
    }

    public void setPartOfSpeechQualifier(CognatePartOfSpeechQualifier partOfSpeechQualifier) {
        this.partOfSpeechQualifier = partOfSpeechQualifier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cognate cognate = (Cognate) o;
        if (cognate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cognate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cognate{" +
            "id='" + id + '\'' +
            ", link=" + link +
            ", cognateQualifiers=" + cognateQualifiers +
            ", nounTypesQualifier=" + nounTypesQualifier +
            ", partOfSpeechQualifier=" + partOfSpeechQualifier +
            '}';
    }
}
