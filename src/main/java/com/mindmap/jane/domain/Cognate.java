package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.CognateNounTypesQualifier;
import com.mindmap.jane.domain.enumeration.CognatePartOfSpeechQualifier;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Cognate. Template: {{pokrewne}}
 */
public class Cognate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("link")
    private Link link;

    @Field("cognate_qualifiers")
    private List<String> cognateQualifiers;

    // If cognate is noun then information about type should be provided.
    @Field("gender_qualifier")
    private CognateNounTypesQualifier nounTypesQualifier;

    @Field("part_of_speech_qualifier")
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cognate cognate = (Cognate) o;
        return Objects.equals(link, cognate.link) &&
            Objects.equals(cognateQualifiers, cognate.cognateQualifiers) &&
            nounTypesQualifier == cognate.nounTypesQualifier &&
            partOfSpeechQualifier == cognate.partOfSpeechQualifier;
    }

    @Override
    public int hashCode() {

        return Objects.hash(link, cognateQualifiers, nounTypesQualifier, partOfSpeechQualifier);
    }

    @Override
    public String toString() {
        return "Cognate{" +
            "link=" + link +
            ", cognateQualifiers=" + cognateQualifiers +
            ", nounTypesQualifier=" + nounTypesQualifier +
            ", partOfSpeechQualifier=" + partOfSpeechQualifier +
            '}';
    }
}
