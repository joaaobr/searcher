package com.searcher.vsm.document.index

import com.searcher.vsm.document.index.LinkedList
import scala.collection.mutable.HashMap

case class Value[K, V](value: V, key: K)

class HashTable[K, V](size: Int) {
  private val array = Array.fill[LinkedList[Value[K, V]]](size)(null)

  private def hash(key: K): Int = math.abs(key.hashCode() % size)

  def put(key: K, value: V): Unit = {
    val index = hash(key)

    if(array(index) == null) {
      array(index) = new LinkedList[Value[K, V]](new Value(value, key))
    } else {
      array(index).insert(new Value(value, key))
    }
  }

  def get(key: K, default: Value[K, V]): Value[K, V] = {
    val index = hash(key)

    if(array(index) == null) {
      return default
    }
    array(index).getByOrElse(value => value.key == key, default)
  }

  def update(key: K, value: V) = {
    val index = hash(key)

    if(array(index) == null) {
      put(key, value)
    } else {
      array(index) = array(index).update(value => value.key == key, new Value(value, key))
    }
  }
}