package com.searcher.vsm

/*
    This module was created to handle VSM(Vector Space Model) calculations.
*/

object SimilarityCosine {
    private val SQUARE: Int = 2

    /*
        Example:
          input -> (1, 0, 1), (1, 1, 0)
          output -> 
            (1 * 1), (1 * 0), (1 * 0)
            (1 + 0 + 0)
                /
            sqrt(pow(1) + pow(1)) + sqrt(pow(1) + pow(0)) + sqrt(pow(1) + pow(0))
    */
    def calculateSimilarityOfCosine(vector1: Seq[Int], vector2: Seq[Int]): Double = {
        val v1 = vector1
        .zip(vector2)
        .map((x, y) => x * y)
        .reduce((x, y) => x + y)

        val v2 = math.sqrt(
            vector1
            .map(x => math.pow(x, SQUARE))
            .reduce((x, y) => x + y)
        )

        val v3 = math.sqrt(
            vector2
            .map(x => math.pow(x, SQUARE))
            .reduce((x, y) => x + y)
        )


        (v1 / (v2 * v3))
    }


    /*
        Example: 
            vectors -> ((1, 0, 1), (0, 1, 0), (1, 1, 0))
            query -> (1, 1, 1)
    */
    def calculateNearestVector(vectors: Seq[SearchVector], query: Seq[Int]): Seq[SearchVectorResult] = {
        vectors.map(x => 
            new SearchVectorResult(x.id, calculateSimilarityOfCosine(x.vector, query))
        )
    }
}