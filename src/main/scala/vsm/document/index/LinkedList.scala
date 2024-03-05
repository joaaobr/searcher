package com.searcher.vsm.document.index

class LinkedList[T](value: T) {
    var current: T = value
    var next: LinkedList[T] = null

    def this(n: LinkedList[T]) = {
        this(n.current)
        next = n.next
    }

    def isEmpty: Boolean = current == null

    def isPresent: Boolean = !isEmpty

    private def insert0(value: T, currentNode: LinkedList[T]): LinkedList[T] = {
        if(currentNode.next != null) {
            return insert0(value, currentNode.next)
        }

        val newNode = new LinkedList[T](value)
        currentNode.next = newNode
        return currentNode
    }

    def insert(value: T) = {
        insert0(value, this)
    }

    private def get0(value: T, currentNode: LinkedList[T]): T = {
        if(currentNode.current == value) {
            return value
        }

        return get0(value, currentNode.next)
    }

    def get(value: T): T = {
        get0(value, this)
    }

    private def getByOrElse0(f: T => Boolean, currentNode: LinkedList[T], default: T): T = {
        if(currentNode == null) {
            return default
        }
        
        val operation: Boolean = f(currentNode.current)
        if(operation) {
            return currentNode.current
        }

        return getByOrElse0(f, currentNode.next, default)
    }

    def getByOrElse(f: T => Boolean, default: T): T = {
        getByOrElse0(f, this, default)
    }

    def update0(f: T => T, currentNode: LinkedList[T], newLinkedList: LinkedList[T]): LinkedList[T] = {
        if(currentNode == null) {
            return newLinkedList
        }

        newLinkedList.current = f(currentNode.current)

        return update0(f, currentNode.next, newLinkedList)
    }

    def map(f: T => T): LinkedList[T] = {
        update0(f, this, new LinkedList[T](this.current))
    }

    def update1(f: T => Boolean, newValue: T, currentNode: LinkedList[T], newLinkedList: LinkedList[T]): LinkedList[T] = {
        if(currentNode == null) {
            return newLinkedList
        }

        val operation: Boolean = f(currentNode.current)
        if(operation) {
            newLinkedList.current = newValue
            return newLinkedList
        }

        return update1(f, newValue, currentNode.next, newLinkedList.next)
    }

    def update(f: T => Boolean, newValue: T): LinkedList[T] = {
        update1(f, newValue, this, new LinkedList[T](this))
    }
}
