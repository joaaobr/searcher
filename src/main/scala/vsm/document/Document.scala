package com.searcher.vsm.document

import java.text.Normalizer
import java.util.regex.Pattern

/*
    This module cleans the strings by removing characters that are useless for searching.
*/

object Document {
    private def removeAccents(value: String): String = {
        val normalizer = Normalizer.normalize(value, Normalizer.Form.NFD);
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }

    private def removePuncts(value: String): String = value.replaceAll("\\p{Punct}", "")

    private def removePunctsAndAccents(value: String): String = removeAccents(removePuncts(value))

    private def filterByLengthLessThanTwo(x: String): Boolean = x.length >= 2

    def clear(document: String): Seq[String] = {
        removePunctsAndAccents(
            document
            .toLowerCase()
            .trim()
        )
        .split(' ')
        .filter(filterByLengthLessThanTwo)
        .toSeq
    }
}