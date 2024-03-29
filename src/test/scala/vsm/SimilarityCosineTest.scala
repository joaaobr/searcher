import com.searcher.vsm._
import com.searcher.vsm.document._

class SimilarityCosineTest extends munit.FunSuite {
  test("Calculate similarity") {
    val result = SimilarityCosine.calculateSimilarityOfCosine(Seq(1, 1), Seq(1, 0))
    val expected = 0.7071067811865475
    assert(result == expected)
  }

  test("Calculate similarity are equals an 1") {
    val result = SimilarityCosine.calculateSimilarityOfCosine(Seq(1, 1), Seq(1, 1))
    val expected = 0.9999999999999998
    assert(result == expected)
  }

  test("Calculate nearest vector") {
    val vectors: Seq[SearchVector] = Seq(
      SearchVector(1, Seq(0, 1, 1, 1)),
      SearchVector(2, Seq(1, 0, 1, 0)),
      SearchVector(3, Seq(0, 1, 0, 2)),
      SearchVector(4, Seq(1, 3, 0, 2)),
    )
    val query: Seq[Int] = Seq(1, 2, 0, 1)

    val result = SimilarityCosine.calculateNearestVector(vectors, query)
    val expected = Seq(
      SearchVectorResult(1, 0.7071067811865476),
      SearchVectorResult(2, 0.2886751345948129),
      SearchVectorResult(3, 0.7302967433402214),
      SearchVectorResult(4, 0.9819805060619657)
    )
    assert(result == expected)
  }

  test("Calculate nearest vector and return 0") {
    val vectors: Seq[SearchVector] = Seq(
      SearchVector(1, Seq(5, 0, 0, 2)),
    )
    val query: Seq[Int] = Seq(0, 1, 1, 0)

    val result = SimilarityCosine.calculateNearestVector(vectors, query)

    val expected = Seq(
      SearchVectorResult(1, 0.0),
    )
    assert(result == expected)
  }
}
