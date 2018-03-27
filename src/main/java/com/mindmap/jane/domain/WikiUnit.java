package com.mindmap.jane.domain;

import com.mindmap.jane.domain.enumeration.CognatePartOfSpeechQualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * A WikiUnit.
 */
@Document(collection = "wiki_unit")
public class WikiUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    @Indexed
    private String name;

    @Field("sentence_name")
    private Sentence sentenceName;

    @Field("topic")
    @Indexed
    private String topic;

    @Field("meanings")
    private List<Meaning> meanings = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public WikiUnit name(String name) {
        this.name = name;
        return this;
    }

    public Sentence getSentenceName() {
        return sentenceName;
    }

    public void setSentenceName(Sentence sentenceName) {
        this.sentenceName = sentenceName;
    }

    public WikiUnit sentenceName(Sentence sentenceName) {
        this.sentenceName = sentenceName;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public WikiUnit topic(String topic) {
        this.topic = topic;
        return this;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void addMeaning(Meaning meaning) {
        meanings.add(meaning);
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public void addExample(List<Numeration> numerations, Example example) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getExamples().add(example);
            }
        }
    }

    public void addCollocation(List<Numeration> numerations, Collocation collocation) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if(meaning != null) {
                meaning.getCollocations().add(collocation);
            }
        }
    }

    public void addSynonym(List<Numeration> numerations, Synonym synonym) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getSynonyms().add(synonym);
            }
        }
    }

    public void addAntonym(List<Numeration> numerations, Antonym antonym) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getAntonyms().add(antonym);
            }
        }
    }

    public void addCognate(Cognate cognate) {
        CognatePartOfSpeechQualifier qualifiers = cognate.getPartOfSpeechQualifier();

        if (qualifiers == null) {
            for (Meaning meaning : meanings) {
                meaning.getCognates().add(cognate);
            }
        } else {
            for (Meaning meaning : meanings) {
                for (String meaningQualifier : meaning.getPartOfSpeechQualifiers()) {
                    if (meaningQualifier.contains(qualifiers.value)) {
                        meaning.getCognates().add(cognate);
                        return;
                    }
                }
            }
        }
    }

    public void addPhraseology(List<Numeration> numerations, Phraseology phraseology) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getPhraseology().add(phraseology);
            }
        }
    }

    public void addNounVar(List<Numeration> numerations, NounVar nounVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getNounVars().add(nounVar);
            }
        }
    }

    public void addVerbVar(List<Numeration> numerations, VerbVar verbVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getVerbVars().add(verbVar);
            }
        }
    }

    public void addAdjectiveVar(List<Numeration> numerations, AdjectiveVar adjectiveVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getAdjectiveVars().add(adjectiveVar);
            }
        }
    }

    public void addAdverbVar(List<Numeration> numerations, AdverbVar adverbVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getAdverbVars().add(adverbVar);
            }
        }
    }

    public void addPronounVar(List<Numeration> numerations, PronounVar pronounVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getPronounVars().add(pronounVar);
            }
        }
    }

    public void addNumeralVar(List<Numeration> numerations, NumeralVar numeralVar) {
        for (Numeration numeration : numerations) {
            Meaning meaning = getMeaning(numeration);
            if (meaning != null) {
                meaning.getNumeralVars().add(numeralVar);
            }
        }
    }

    public Meaning getMeaning(Numeration numeration) {
        for (Meaning meaning : meanings) {
            if (meaning.getNumeration().equals(numeration)) {
                return meaning;
            }
        }
        return null;
    }

    public List<Numeration> getMeaningsByPartOfSpeechNumber(short partOfSpeechNumber) {
        return meanings.stream()
            .map(Meaning::getNumeration)
            .filter(numeration -> numeration.getPartOfSpeech() == partOfSpeechNumber)
            .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WikiUnit wikiUnit = (WikiUnit) o;
        return Objects.equals(id, wikiUnit.id) &&
            Objects.equals(name, wikiUnit.name) &&
            Objects.equals(topic, wikiUnit.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, topic);
    }

    @Override
    public String toString() {
        return "WikiUnit{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", topic='" + topic + '\'' +
            '}';
    }

}
