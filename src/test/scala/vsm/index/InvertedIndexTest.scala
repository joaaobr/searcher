import com.searcher.vsm.document.index.InvertedIndex

class InvertedIndexTest extends munit.FunSuite {
  test("Insersion and get value") {
    val indexer = new InvertedIndex()
    val preset = Seq("key")

    indexer.insertSeq(preset, 5)

    val result = indexer.get("key")
    val expected = Set(5)

    assert(result == expected)
  }

  test("Find empty value") {
    val indexer = new InvertedIndex()

    val result = indexer.get("empty")
    val expected = Set()

    assert(result == expected)
  }

  test("Find key with multiples ids") {
    val indexer = new InvertedIndex()
    val preset = Seq("key")

    indexer.insertSeq(preset, 5)
    indexer.insertSeq(preset, 6)
    indexer.insertSeq(preset, 7)

    val result = indexer.get("key")
    val expected = Set(5, 6, 7)

    assert(result == expected)
  }

  test("Find keys with multiples ids") {
    val indexer = new InvertedIndex()
    val preset = Seq("key")
    val preset2 = Seq("word")

    indexer.insertSeq(preset, 5)
    indexer.insertSeq(preset, 6)
    indexer.insertSeq(preset, 7)

    indexer.insertSeq(preset2, 8)
    indexer.insertSeq(preset2, 9)
    indexer.insertSeq(preset2, 10)

    val result = indexer.getIds(Seq("key", "word"))
    val expected = Set(5, 6, 7, 8, 9, 10)

    assert(result == expected)
  }

  test("Get empty value") {
    val indexer = new InvertedIndex()

    val result = indexer.getIds(Seq("empty"))
    val expected = Set()

    assert(result == expected)
  }

  test("Update value") {
    val indexer = new InvertedIndex()
    val preset = Seq("key")

    indexer.insertSeq(preset, 5)
    indexer.insertSeq(preset, 6)
    indexer.update("key", 7)

    val result = indexer.getIds(Seq("key"))
    val expected = Set(5, 6, 7)

    assert(result == expected)
  }

}
