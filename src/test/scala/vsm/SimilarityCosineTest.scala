import com.searcher.vsm._

class SimilarityCosineTest extends munit.FunSuite {
  test("Test calculate similarity") {
    val result = SimilarityCosine.calculateSimilarityOfCosine(Seq(1, 1), Seq(1, 0))
    val expected = 0.7071067811865475
    assert(result == expected)
  }

  test("Test calculate similarity are equals an 1") {
    val result = SimilarityCosine.calculateSimilarityOfCosine(Seq(1, 1), Seq(1, 1))
    val expected = 0.9999999999999998
    assert(result == expected)
  }

  test("Test calculate nearest vector") {
    val vectors:Seq[SearchVector] = Seq(
      new SearchVector(1, Seq(0, 1, 1, 1)),
      new SearchVector(2, Seq(1, 0, 1, 0)),
      new SearchVector(3, Seq(0 ,1, 0, 2)),
      new SearchVector(4, Seq(1 ,3, 0, 2)),
    )
    val query: Seq[Int] = Seq(1, 2, 0, 1)

    val result = SimilarityCosine.calculateNearestVector(vectors, query)
    val expected = Seq(
      new SearchVectorResult(1, 0.7071067811865476),
      new SearchVectorResult(2, 0.2886751345948129),
      new SearchVectorResult(3, 0.7302967433402214),
      new SearchVectorResult(4, 0.9819805060619657)
    )
    assert(result == expected)
  }
}
