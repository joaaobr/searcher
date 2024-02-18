package com.searcher.vsm

/*
    This module stores string sequences.
*/

class Document(document: String) {
    def splitDocumentToSpaces(): Seq[String] = {
        document
        .toLowerCase()
        .split(' ')
        .toSeq
    }
}