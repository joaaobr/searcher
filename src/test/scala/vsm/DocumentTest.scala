import com.searcher.vsm.document.Document

class DocumentTest extends munit.FunSuite {
  test("Divide the document by spaces") {
    val result = Document.clear("Hello World")
    val expected = Seq("hello", "world")

    assert(result == expected)
  }

  test("Divide the document by spaces and remove punctuations") {
    val result = Document.clear("Hello, World!")
    val expected = Seq("hello", "world")

    assert(result == expected)
  }

  test("Pass an empty string with parameter") {
    val result = Document.clear("")
    val expected = Seq()

    assert(result == expected)
  }

  test("Divide the document by spaces and remove punctuations and accents") {
    val result = Document.clear(" hÉllo, a world ")
    val expected = Seq("hello", "world")

    assert(result == expected)
  }
}
