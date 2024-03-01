package com.searcher.vsm.document.index

import scala.collection.mutable.HashMap

/*
    This module is used to index phrases and to facilitate the search and obtain the closest documents.
*/

class InvertedIndex {
    val index: HashMap[String, Set[Int]] = new HashMap[String, Set[Int]]

    def get(key: String): Set[Int] = {
        index.get(key).get
    }

    def insertSeq(keys: Seq[String], value: Int) = {
        keys
        .map(key => update(key, value))
    }

    def update(key: String, id: Int) = {
        val ids = index.getOrElseUpdate(key, Set()) ++ Set(id)
        index.update(key, ids)
    }

    def getKeys(keys: Seq[String]) = {
        keys
        .map(key => index.getOrElseUpdate(key, Set()))
        .reduce((a, b) => a ++ b)
        .toSet
    }
}