package com.searcher.vsm

import scala.util.hashing.MurmurHash3
import java.util.BitSet

/*
    This class is an abstraction of Bloom filter. The difference is that it does not store 0 and 1,
    it stores the number of identical words entered.
*/

class BloomFilter(size: Int, hashTable: BitBloom) {
    val SEEDS: Seq[Int] = Seq(0xFF0, 0x999, 0xFFF, 0xAF)

    def this(size: Int) = {
        this((size * 2) + 21, new BitBloom((size * 2) + 21))
    }

    private def hashing(str: String) = {
        val hash = SEEDS
        .map(seed => MurmurHash3.stringHash(str, seed))
        .reduce((a, b) => (a * b))

        (math.abs(hash) + str.hashCode()) % size
    }

    def add(str: String) = {
        val index = hashing(str)
        hashTable.set(index)
    }

    def get(str: String): Int = {
        val index = hashing(str)
        hashTable.get(index)
    }

    def isPresent(str: String): Boolean = {
        val index = hashing(str)
        hashTable.isPresent(index)
    }
}