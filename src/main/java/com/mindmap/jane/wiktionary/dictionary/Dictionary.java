package com.mindmap.jane.wiktionary.dictionary;

import com.mindmap.jane.domain.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Container for all units parsed form Wiktionary database dump.
 *
 * @author Sebastian Fabisz
 */
public class Dictionary {

    private Set<SourceWikiUnit> sourceWikiUnits = new HashSet<>();

    private Set<WikiUnit> wikiUnits = new HashSet<>();

	public Dictionary() {
	}

    public final Set<SourceWikiUnit> getSourceWikiUnits() {
        return sourceWikiUnits;
    }

    public final void setSourceWikiUnits(final Set<SourceWikiUnit> rawUnits) {
        this.sourceWikiUnits = rawUnits;
    }

    public final Set<WikiUnit> getWikiUnits() {
        return wikiUnits;
    }

    public final void setWikiUnits(Set<WikiUnit> wikiUnits) {
        this.wikiUnits = wikiUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(sourceWikiUnits, that.sourceWikiUnits) &&
            Objects.equals(wikiUnits, that.wikiUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceWikiUnits, wikiUnits);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dictionary{");
        sb.append("sourceWikiUnits=").append(sourceWikiUnits);
        sb.append(", wikiUnits=").append(wikiUnits);
        sb.append('}');
        return sb.toString();
    }
}
