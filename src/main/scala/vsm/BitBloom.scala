package com.searcher.vsm

import scala.collection.mutable.ArrayBuffer

class BitBloom(size: Int, buffer: ArrayBuffer[Int]) {
    def this(size: Int) = this(size, ArrayBuffer.fill(size)(0))

    def set(pos: Int) = {
        buffer(pos) = buffer(pos) + 1
    }

    def get(pos: Int): Int = {
        buffer(pos)
    }

    def isPresent(pos: Int): Boolean = {
        buffer(pos) >= 1
    }
}