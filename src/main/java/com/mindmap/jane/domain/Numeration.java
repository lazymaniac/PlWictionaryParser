package com.mindmap.jane.domain;

import java.util.Objects;

public class Numeration {

	private byte partOfSpeechIdx;

	private byte importanceIdx;

	public Numeration(byte partOfSpechIdx, byte importanceIdx) {
		this.partOfSpeechIdx = partOfSpechIdx;
		this.importanceIdx = importanceIdx;
	}

	public Numeration() {
	}

	public byte getPartOfSpeech() {
		return partOfSpeechIdx;
	}

	public byte getImportanceIdx() {
		return importanceIdx;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Numeration that = (Numeration) o;
        return partOfSpeechIdx == that.partOfSpeechIdx &&
            importanceIdx == that.importanceIdx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partOfSpeechIdx, importanceIdx);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Numeration{");
        sb.append("partOfSpeechIdx=").append(partOfSpeechIdx);
        sb.append(", importanceIdx=").append(importanceIdx);
        sb.append('}');
        return sb.toString();
    }
}
