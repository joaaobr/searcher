package com.searcher.vsm.document

import com.searcher.vsm._
import scala.collection.mutable.HashMap

/*
    This class is used to compare vectors with the query individually.
*/
class IndividualComparisonVector(documents: HashMap[Int, Seq[String]], query: Seq[String]) {
    private def removeQueryOfDocuments() = documents -= -1

    /*
        Compares each vector with the query individually.
    */
    private def getVectorComparisonObject(id: Int, document: Seq[String]): ComparisonVectors = {
        val vectorSearch = (document ++ query).toSet

        new ComparisonVectors(
            id, 
            Phrases.compareVectors(vectorSearch, document),
            Phrases.compareVectors(vectorSearch, query)
        )
    }

    private def getComparisonVectors(): Seq[ComparisonVectors] = {
        documents
        .map(getVectorComparisonObject)
        .toSeq
    }

    /*
        Iterates over each document individually comparing the document with the query.
    */

    def resultComparisonVectors(): SearcherResult = {
        removeQueryOfDocuments()
        val spaceVector = getComparisonVectors()

        val result = spaceVector
        .map(comparisonVectors => new SearchVectorResult(
            comparisonVectors.id,
            SimilarityCosine.calculateSimilarityOfCosine(comparisonVectors.vectorSearch, comparisonVectors.query)
        ))
        new SearcherResult(result)
    }
}