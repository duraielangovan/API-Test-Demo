package com.securonix.at.common.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;

public class CustomCharacterEscapes extends CharacterEscapes {
    private static final long serialVersionUID=1L;
    private final int[] asciiEscapes;

    public CustomCharacterEscapes() {
        this.asciiEscapes = standardAsciiEscapesForJSON();
        //Jackson standard escape table has " , to override it
        asciiEscapes['"']= CharacterEscapes.ESCAPE_NONE;
    }


    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int i) {
        return null;
    }
}
