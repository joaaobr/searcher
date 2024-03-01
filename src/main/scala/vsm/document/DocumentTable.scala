package com.searcher.vsm.document

import scala.collection.mutable.HashMap
import com.searcher.vsm._
import com.searcher.vsm.document.index.InvertedIndex

case class SearchVector(id: Int, vector: Seq[Int])

/*
    This module is used to store documents and perform searches based on the vector space model.
*/

class DocumentTable(documents: HashMap[Int, Seq[String]]) {
    private var currentId: Int = 0
    private var queryVector: Seq[Int] = Seq()
    private val indexer: InvertedIndex = InvertedIndex()

    def this() = this(new HashMap[Int, Seq[String]])

    private def incrementCurrentId = {
        currentId += 1
    }

    private def setQueryVector(searchVector: Set[String], query: Seq[String]) = {
        queryVector = Phrases.compareVectors(searchVector, query)
    }

    private def getQuery(): Seq[String] = documents.getOrElseUpdate(-1, Seq())

    /*
        Push to hash table with incremental id.
    */
    def pushText(text: String) = {
        val document = Document.clear(text)
        incrementCurrentId
        documents.put(currentId, document)
        indexer.insertSeq(document, currentId)
    }

    def pushFile(file: String) = {
        val document = new DocumentFile(file).read()
        incrementCurrentId
        documents.put(currentId, document)
        indexer.insertSeq(document, currentId)
    }

    /*
        Push to hash table with negative id.
    */
    def pushQuery(text: String) = {
        val document = Document.clear(text)
        documents.put(-1, document)
    }

    def pushQueryFile(file: String) = {
        val document = new DocumentFile(file).read()
        documents.put(-1, document)
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

        val query = getQuery()

        setQueryVector(searchVector, query)

        documents
        .map((id, document) => new SearchVector(id, Phrases.compareVectors(searchVector, document)))
        .toSeq
    }

    def resultComparisonVectors(): SearcherResult = {
        val individualComparisonVector = new IndividualComparisonVector(documents, getQuery())
        val result = individualComparisonVector.resultComparisonVectors()

        new SearcherResult(result)
    }

    /*
        Returns the similarity of the search vectors with the query.
    */
    def result(): SearcherResult = {
        val spaceVector = getSearchVectors()

        val result = SimilarityCosine.calculateNearestVector(spaceVector, queryVector)
        .filter(vector => vector.id > 0)
        new SearcherResult(result)
    }

    def getSearchVectorsWithIndex(): Seq[SearchVector] = {
        val query = getQuery()

        val documentsIndexed = indexer
        .getKeys(query)
        .toSeq
        .map(id => (id, documents.getOrElseUpdate(id, Seq())))
        .toMap

        val searchVector = documentsIndexed
        .map((_, document) => document)
        .reduce((a, b) => a ++ b)
        .toSet


        setQueryVector(searchVector, query)

        documentsIndexed
        .map((id, document) => new SearchVector(id, Phrases.compareVectors(searchVector, document)))
        .toSeq
    }

    def resultWithIndex(): SearcherResult = {
        val spaceVector = getSearchVectorsWithIndex()

        val result = SimilarityCosine.calculateNearestVector(spaceVector, queryVector)
        .filter(vector => vector.id > 0)
        new SearcherResult(result)
    }
}