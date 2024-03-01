package com.searcher.vsm.document.index

import scala.collection.mutable.HashMap

class InvertedIndex {
    val table: HashMap[String, Set[Int]] = new HashMap[String, Set[Int]]

    def get(key: String): Set[Int] = {
        table.get(key).get
    }

    def insertSeq(keys: Seq[String], value: Int) = {
        keys
        .map(key => update(key, value))
        
    }

    def update(key: String, id: Int) = {
        val ids = table.getOrElseUpdate(key, Set()) ++ Set(id)
        table.update(key, ids)
    }

    def getKeys(keys: Seq[String]) = {
        keys
        .map(key => table.getOrElseUpdate(key, Set()))
        .reduce((a, b) => a ++ b)
        .toSet
    }
}