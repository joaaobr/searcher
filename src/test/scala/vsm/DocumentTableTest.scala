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
      new SearchVectorResult(1, 0.6666666666666667),
      new SearchVectorResult(2, 1.0000000000000002)
    )

    assert(result == expected)
  }

  test("Insert document inconpatival") {
    val table = new DocumentTable()
    
    table.pushText("Document inconpatival")
    table.pushQuery("Testing")

    val result = table.result().first()
    val expected = new SearchVectorResult(1, 0.0)
    assert(result == expected)
  }

  test("Read file") {
    val table = new DocumentTable()
    
    table.pushText("Document inconpatival")
    table.pushFile("file-test")
    table.pushQuery("Now is the winter of our discontent Made glorious summer by this sun of York;")

    val result = table.result().all()

    val expected = Seq(
      new SearchVectorResult(1, 0.19069251784911848),
      new SearchVectorResult(2, 0.5645990940073806)
    )
    
    assert(result == expected)
  }
}
