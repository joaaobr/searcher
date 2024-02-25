package com.searcher.vsm

import scala.collection.mutable.ArrayBuffer

class BitBloom(size: Int, buffer: ArrayBuffer[Int]) {
    def this(size: Int) = this(size, ArrayBuffer.fill(size)(0))

    def set(position: Int) = {
        buffer(position) = buffer(position) + 1
    }

    def get(position: Int): Int = {
        buffer(position)
    }

    def isPresent(position: Int): Boolean = {
        buffer(position) >= 1
    }
}