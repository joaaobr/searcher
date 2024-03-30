import com.searcher.vsm.document._
import com.searcher.vsm._
import scala.collection.mutable.HashMap

class IndividualComparisonVectorTest extends munit.FunSuite {
  test("Simple insertion") {
    val documents = HashMap(
        1 -> Seq("AB", "CD"),
        2 -> Seq("AC", "CD"),
    )

    val table = new IndividualComparisonVector(Seq("AB", "b"), documents.toMap)

    val result = table.resultComparisonVectors()

    val expected = Seq(
      SearchVectorResult(1, 0.4999999999999999),
      SearchVectorResult(2, 0.0)
    )

    assert(result == expected)
  }

  test("Insert and get 1 with result") {
    val documents = HashMap(
        1 -> Seq("equals"),
    )

    val table = new IndividualComparisonVector(Seq("equals"), documents.toMap)

    val result = table.resultComparisonVectors()

    val expected = Seq(
      SearchVectorResult(1, 1.0),
    )

    assert(result == expected)
  }

  test("Insert and get 0 with result") {
    val documents = HashMap(
        1 -> Seq("equals"),
    )

    val table = new IndividualComparisonVector(Seq("not"), documents.toMap)

    val result = table.resultComparisonVectors()

    val expected = Seq(
      SearchVectorResult(1, 0.0),
    )

    assert(result == expected)
  }

  test("Insert and get two vector with 1 with result") {
    val documents = HashMap(
        1 -> Seq("equals"),
        2 -> Seq("equals", "equals")
    )

    val table = new IndividualComparisonVector(Seq("equals"), documents.toMap)

    val result = table.resultComparisonVectors()

    val expected = Seq(
      SearchVectorResult(1, 1.0),
      SearchVectorResult(2, 1.0),
    )

    assert(result == expected)
  }
}
