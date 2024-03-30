package com.searcher.vsm.document

import com.searcher.vsm._
import scala.collection.mutable.HashMap

case class ComparisonVectors(id: Int, vectorSearch: Seq[Int], query: Seq[Int])

/*
    This class is used to compare vectors with the query individually.
*/
class IndividualComparisonVector(query: Seq[String], documents: Map[Int, Seq[String]]) {
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
        .filter((id, _) => id > 0)
        .map(getVectorComparisonObject)
        .toSeq
    }

    /*
        Iterates over each document individually comparing the document with the query.
    */

    def resultComparisonVectors(): Seq[SearchVectorResult] = {
        val spaceVector = getComparisonVectors()

        spaceVector
        .map(comparisonVectors => new SearchVectorResult(
            comparisonVectors.id,
            SimilarityCosine.calculateSimilarityOfCosine(comparisonVectors.vectorSearch, comparisonVectors.query)
        ))
    }
}