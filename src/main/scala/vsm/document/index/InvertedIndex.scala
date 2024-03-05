package com.searcher.vsm.document.index

import scala.collection.mutable.HashMap
import com.searcher.vsm.document.index._


/*
    This module is used to index phrases and to facilitate the search and obtain the closest documents.
*/

class InvertedIndex {
    val index: HashTable[String, Set[Int]] = new HashTable[String, Set[Int]](500)

    def get(key: String): Set[Int] = {
        index.get(key, new Value(Set(), "empty")).value
    }

    def insertSeq(keys: Seq[String], value: Int) = {
        keys
        .map(key => update(key, value))
    }

    def update(key: String, id: Int) = {
        val ids = get(key) ++ Set(id)
        index.update(key, ids)
    }

    def getIds(keys: Seq[String]): Set[Int] = {
        keys
        .map(key => get(key))
        .reduce((a, b) => a ++ b)
    }
}