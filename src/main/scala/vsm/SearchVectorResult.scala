package com.searcher.vsm

case class SearchVectorResult(id: Int, value: Double)

class SearcherResult(result: Seq[SearchVectorResult]) {
    def take(n: Int): Seq[SearchVectorResult] = {
        result.take(n)
    }

    def first(): SearchVectorResult = {
        result
        .sortWith((x, y) => x.value < y.value)
        .last 
    }

    def order(): Seq[SearchVectorResult] = {
        result
        .sortBy((x) => x.value)
    }

    def all(): Seq[SearchVectorResult] = result

    def show() = {
        println("Id  Similarity")
        result
        .map(x => println(x.id + "   " + x.value))
    }
}