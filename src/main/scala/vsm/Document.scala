package com.searcher.vsm

/*
    This module stores string sequences.
*/

class Document(document: String) {
    def clearAndSplit(): Seq[String] = {
        document
        .toLowerCase()
        .trim()
        .replaceAll("\\p{Punct}", "")
        .split(' ')
        .filter(x => x.length >= 2)
        .toSeq
    }
}