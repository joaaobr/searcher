import com.searcher.vsm._

class PhrasesTest extends munit.FunSuite {
  test("Compare two vectors") {
    val searchVector = Set("A", "B", "C", "D")
    val document = Seq("B", "D", "A", "C")

    val result = Phrases.compareVectors(searchVector, document)
    val expected = Seq(1, 1, 1, 1)

    assert(result == expected)
  }

  test("Compare two vectors and get a vector with zeros") {
    val searchVector = Set("O", "K", "4", "Y")
    val document = Seq("B", "D", "A")

    val result = Phrases.compareVectors(searchVector, document)
    val expected = Seq(0, 0, 0, 0)

    assert(result == expected)
  }

  test("Compare two vectors and get a vector with a 0") {
    val searchVector = Set("V")
    val document = Seq("B", "D", "A")

    val result = Phrases.compareVectors(searchVector, document)
    val expected = Seq(0)

    assert(result == expected)
  }
}
