import com.searcher.vsm.document._
import com.searcher.vsm._

class DocumentTableTest extends munit.FunSuite {
  test("Insert document and get result") {
    val table = new DocumentTable()

    table.pushText("This is failure")
    table.pushText("This is ok")
    table.pushQuery("This is ok")

    val result = table.result().all()
    val expected = Seq(
      SearchVectorResult(1, 0.6666666666666667),
      SearchVectorResult(2, 1.0000000000000002)
    )

    assert(result == expected)
  }

  test("Insert document with low similarity") {
    val table = new DocumentTable()
    
    table.pushText("Document inconpatival")
    table.pushQuery("Testing")

    val result = table.result().first()
    val expected = new SearchVectorResult(1, 0.0)
    assert(result == expected)
  }

  test("Insert document with high similarity") {
    val table = new DocumentTable()
    
    table.pushText("similarity")
    table.pushQuery("similarity")

    val result = table.result().first()
    val expected = new SearchVectorResult(1, 1.0)

    assert(result == expected)
  }

  test("Insert and get file similarity") {
    val table = new DocumentTable()
    
    table.pushText("Document inconpatival")
    table.pushFile("file-test")
    table.pushQuery("Now is the winter of our discontent Made glorious summer by this sun of York;")

    val result = table.result().all()

    val expected = Seq(
      SearchVectorResult(1, 0.19069251784911848),
      SearchVectorResult(2, 0.5645990940073806)
    )

    assert(result == expected)
  }

  test("Insert and get file query similarity") {
    val table = new DocumentTable()
    
    table.pushText("Lorem ipsum dolor sit amet.")
    table.pushText("no")
    table.pushQueryFile("query-file-test")

    val result = table.result().all()

    val expected = Seq(
      SearchVectorResult(1, 0.3913968438320653),
      SearchVectorResult(2, 0.0)
    )

    assert(result == expected)
  }

  test("Insert and get file query similarity with comparison vector") {
    val table = new DocumentTable()
    
    table.pushText("Lorem ipsum dolor sit amet.")
    table.pushText("no")
    table.pushQueryFile("query-file-test")

    val result = table.resultComparisonVectors().all()

    val expected = Seq(
      SearchVectorResult(1, 0.3913968438320653),
      SearchVectorResult(2, 0.0)
    )

    assert(result == expected)
  }

  test("Insert and get result with comparison vector") {
    val table = new DocumentTable()
    
    table.pushText("Lorem ipsum dolor sit amet.")
    table.pushText("no")
    table.pushText("Lorem no")
    table.pushText("Lorem")

    table.pushQueryFile("query-file-test")

    val result = table.resultComparisonVectors().all()

    val expected = Seq(
      SearchVectorResult(1, 0.3913968438320653),
      SearchVectorResult(2, 0.0),
      SearchVectorResult(3, 0.2526455763199557),
      SearchVectorResult(4, 0.309426373877638)
    )

    assert(result == expected)
  }

  test("Insert document with high similarity with comparison vector") {
    val table = new DocumentTable()

    table.pushText("Equals")
    table.pushQuery("Equals")

    val result = table.resultComparisonVectors().all()

    val expected = Seq(
      SearchVectorResult(1, 1.0)
    )

    assert(result == expected)
  }
}
