package com.searcher.vsm

import scala.collection.mutable.HashMap
import com.searcher.vsm.Document

case class SearchVector(id: Int, vector: Seq[Int])

case class SearchVectorResult(id: Int, vector: Double)

/*
    This module is used to store documents and perform searches based on the vector space model.
*/

class DocumentTable(documents: HashMap[Int, Seq[String]]) {
    private var currentId: Int = 0
    private var query: Seq[String] = Seq()
    private var queryVector: Seq[Int] = Seq()

    def this() = this(new HashMap[Int, Seq[String]])

    def incrementCurrentId = {
        currentId += 1
    }

    /*
        Push to hash table with incremental id.
    */
    def pushText(text: String) = {
        val document = new Document(text).splitDocumentToSpaces()
        incrementCurrentId
        documents.put(currentId, document)
    }

    /*
        Push to hash table with negative id.
    */
    def pushQuery(text: String) = {
        val document = new Document(text).splitDocumentToSpaces()
        incrementCurrentId
        documents.put(-1, document)
        query = document
    }

    /*
        Based on the hash table, it creates a search vector and returns comparisons
        between the search vector and the documents.
    */
    def getSearchVectors(): Seq[SearchVector] = {
        val searchVector = documents
        .map((_, document) => document)
        .reduce((a, b) => a ++ b)
        .toSet

        queryVector = Phrases.compareVectors(searchVector, query)

        documents
        .map((x, y) => new SearchVector(x, Phrases.compareVectors(searchVector, y)))
        .toSeq
    }

    /*
        Returns the similarity of the search vectors with the query.
    */
    def result(): Seq[SearchVectorResult] = {
        val spaceVector = getSearchVectors()

        SimilarityCosine.calculateNearestVector(spaceVector, queryVector)
        .filter(result => result.id > 0 && result.vector > 0)
        .sortBy(x => x.vector)
    }
}