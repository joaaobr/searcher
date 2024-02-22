package com.searcher.vsm

import java.text.Normalizer
import java.util.regex.Pattern

/*
    This module stores string sequences.
*/

class Document(document: String) {
    private def removeAccents(value: String): String = {
        val normalizer = Normalizer.normalize(value, Normalizer.Form.NFD);
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    
    }

    private def removePuncts(value: String): String = value.replaceAll("\\p{Punct}", "")

    private def removePunctsAndAccents(value: String): String = {
        removeAccents(
            removePuncts(value)
        )
    }

    def clearAndSplit(): Seq[String] = {
        removePunctsAndAccents(
            document
            .toLowerCase()
            .trim()
        )
        .split(' ')
        .filter(x => x.length >= 2)
        .toSeq
    }
}