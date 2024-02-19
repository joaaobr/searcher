import com.searcher.vsm._

class DocumentTableTest extends munit.FunSuite {
  
  test("Test insert document and get result") {
    val table = new DocumentTable()

    table.pushText("This is failure")
    table.pushText("This is ok")
    table.pushQuery("This is ok")

    val result = table.result()
    val expected = Seq(
      new SearchVectorResult(1, 0.6666666666666667),
      new SearchVectorResult(2, 1.0000000000000002)
    )

    assert(result == expected)
  }

  test("Test insert document inconpatival") {
    val table = new DocumentTable()
    
    table.pushText("Document inconpatival")
    table.pushQuery("Testing")

    val result = table.result()
    val expected = Seq()

    assert(result == expected)
  }
}
