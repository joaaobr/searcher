import com.searcher.vsm.BloomFilter

class BloomFilterTest extends munit.FunSuite {
  val bloom = new BloomFilter(10)

  test("Insertion") {
    bloom.add("hello")
    val expected = true
    val result = bloom.isPresent("hello")

    assert(result == expected)
  }

  test("Insert string with spaces") {
    bloom.add("hello world")
    val expected = 1
    val result = bloom.get("hello world")

    assert(result == expected)
  }

  test("If the key does not exist") {
    val expected = false

    assert(bloom.isPresent("error") == expected)
    assert(bloom.isPresent("hellol") == expected)
    assert(bloom.isPresent("errorl") == expected)
  }

  test("If the key value is increasing") {
    bloom.add("increment")
    bloom.add("increment")
    val expected = 2
    val result = bloom.get("increment")

    assert(result == expected)
  }

  test("If invalid key are equals 0") {
    val expected = 0
    val result = bloom.get("invalid")

    assert(result == expected)
  }

  test("Whether the value of the hello key is equal to 1") {
    val expected = 1
    val result = bloom.get("hello")

    assert(result == expected)
  }
}
