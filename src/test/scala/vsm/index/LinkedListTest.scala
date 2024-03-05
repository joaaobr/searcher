import com.searcher.vsm.document.index.LinkedList

class LinkedListTest extends munit.FunSuite {
  test("Insersion and get value") {
    val linkedList = new LinkedList[Int](10)

    val result = linkedList.get(10)

    val expected = 10

    assert(linkedList.isPresent)
    assert(result == expected)
  }

  test("Find value") {
    val linkedList = new LinkedList[Int](10)
    linkedList.insert(11)
    linkedList.insert(12)
    linkedList.insert(13)

    val result = linkedList.get(12)

    val expected = 12

    assert(linkedList.isPresent)
    assert(result == expected)
  }

  test("Find the non-existent value") {
    val linkedList = new LinkedList[Int](10)
    linkedList.insert(11)
    linkedList.insert(12)
    linkedList.insert(13)

    val result = linkedList.getOrElse(14, 0)

    val expected = 0

    assert(linkedList.isPresent)
    assert(result == expected)
  }

  test("Simple get by value less an 10") {
    val linkedList = new LinkedList[Int](11)
    linkedList.insert(5)
    linkedList.insert(11)
    linkedList.insert(9)

    val result = linkedList.getByOrElse(x => x < 10, 0)

    val expected = 5

    assert(linkedList.isPresent)
    assert(result == expected)
  }

  test("Simple get by value equals an 15") {
    val linkedList = new LinkedList[Int](10)
    linkedList.insert(5)
    linkedList.insert(10)
    linkedList.insert(15)

    val result = linkedList.getByOrElse(x => x == 15, 0)

    val expected = 15

    assert(linkedList.isPresent)
    assert(result == expected)
  }
}
