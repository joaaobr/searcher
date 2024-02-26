package com.searcher.vsm.document

import com.searcher.vsm._
import scala.collection.mutable.HashMap

/*
    This class is used to compare vectors with the query individually.
*/

class IndividualComparisonVector(documents: HashMap[Int, Seq[String]], query: Seq[String]) {
    /*
        Compares each vector with the query individually.
    */
    private def getComparisonVectors(): Seq[ComparisonVectors] = {
        documents
        .map((id, document) =>
            val vectorSearch = (document ++ query).toSet
            
            new ComparisonVectors(
                id, 
                Phrases.compareVectors(vectorSearch, document),
                Phrases.compareVectors(vectorSearch, query)
            )
        )
        .toSeq
    }

    /*
        Iterates over each document individually comparing the document with the query.
    */

    def resultComparisonVectors(): SearcherResult = {
        val spaceVector = getComparisonVectors()

        val result = spaceVector
        .map(x => new SearchVectorResult(
            x.id,
            SimilarityCosine.calculateSimilarityOfCosine(x.vectorSearch, x.query)
        ))
        .filter(vector => vector.id > 0)
        new SearcherResult(result)
    }
}