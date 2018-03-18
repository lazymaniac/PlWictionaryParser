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

    private Set<RawWikiUnit> rawWikiUnits = new HashSet<>();

    private Set<WikiUnit> wikiUnits = new HashSet<>();

	public Dictionary() {
	}

    public final Set<RawWikiUnit> getRawWikiUnits() {
        return rawWikiUnits;
    }

    public final void setRawWikiUnits(final Set<RawWikiUnit> rawUnits) {
        this.rawWikiUnits = rawUnits;
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
        return Objects.equals(rawWikiUnits, that.rawWikiUnits) &&
            Objects.equals(wikiUnits, that.wikiUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawWikiUnits, wikiUnits);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dictionary{");
        sb.append("rawWikiUnits=").append(rawWikiUnits);
        sb.append(", wikiUnits=").append(wikiUnits);
        sb.append('}');
        return sb.toString();
    }
}
