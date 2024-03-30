package com.searcher.vsm.document

import scala.collection.mutable.HashMap
import com.searcher.vsm._
import com.searcher.vsm.document.index.InvertedIndex
import vsm.ConfigSets
import vsm.Config
import scala.collection.Searching.SearchResult

case class SearchVector(id: Int, vector: Seq[Int])

/*
    This module is used to store documents and perform searches based on the vector space model.
*/

class DocumentTable(documents: HashMap[Int, Seq[String]], settings: ConfigSets) {
    private var currentId: Int = 0
    private var queryVector: Seq[Int] = Seq()
    private val indexer: InvertedIndex = InvertedIndex()

    def this() = this(new HashMap[Int, Seq[String]], new Config().default())

    def this(settings: ConfigSets) = this(new HashMap[Int, Seq[String]], settings)

    def this(settings: Config) = this(new HashMap[Int, Seq[String]], settings.get())

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

    def getDocumentsByIndex(): Map[Int, Seq[String]] = {
        val query = getQuery()

        indexer
        .getIds(query)
        .toSeq
        .map(id => (id, documents.getOrElseUpdate(id, Seq())))
        .toMap
    }

    private def getDocuments(): Map[Int, Seq[String]] = {
        if(settings.invertedIndex) {
            return getDocumentsByIndex()
        } else {
           return documents.toMap
        }
    }

    def getSearchVectors(documents: Map[Int, Seq[String]]): Seq[SearchVector] = {
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

    def getSearchResult(documents: Map[Int, Seq[String]]): SearcherResult = {
        if(settings.individualComparisonVector) {
            val individualComparisonVector = new IndividualComparisonVector(getQuery(), documents)
            val result = individualComparisonVector.resultComparisonVectors()

            return new SearcherResult(result)
        } else {
            val result = SimilarityCosine.calculateNearestVector(getSearchVectors(documents), queryVector)
            .filter(vector => vector.id > 0)
            return new SearcherResult(result)
        }
    }

    def result(): SearcherResult = {
        val documents = getDocuments()

        getSearchResult(documents)
    }
}