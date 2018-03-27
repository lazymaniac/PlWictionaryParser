package com.mindmap.jane.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "source_wiki_unit")
public class SourceWikiUnit {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("text")
    private String text;

    public SourceWikiUnit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        this.title = title;
    }

    public final String getText() {
        return text;
    }

    public final void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SourceWikiUnit that = (SourceWikiUnit) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, text);
    }

    @Override
    public String toString() {
        return "SourceWikiUnit{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
