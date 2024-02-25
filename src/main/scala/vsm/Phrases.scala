package com.searcher.vsm
import com.searcher.vsm.BloomFilter

object Phrases {
    private def seqToBloom(set: Seq[String]): BloomFilter = {
        val newBloomFilter = new BloomFilter(set.size)

        set.map(x => newBloomFilter.add(x))
        newBloomFilter
    }

    /*
        Compares two documents and returns an array with the differences
      
        example:
          search document -> ("hello", "world", "searcher")
          document -> ("hello", "main")

        output -> (1, 0, 0)
    */
    def compareVectors(searchDocument: Set[String], document: Seq[String]): Seq[Int] = {
      val documentBloom: BloomFilter = seqToBloom(document)

      searchDocument
      .toSeq
      .map(x => documentBloom.get(x))
    }
}