<h1> What is Searcher ?? </h1>

Searcher is a study project with the objective of using VSM (Vector Space Model) to search documents and reveal the similarity between the document and the query.

## Example:

#### The document table is a module used to store documents and transform them into lists of sentences.
```scala
  val documentTable = new DocumentTable()
```
#### Then we insert the documents into the table:

```scala
  documentTable.pushText("Hello World")
  documentTable.pushText("Hello Guys")
  documentTable.pushText("Hello Main")
  documentTable.pushText("Hello Man Guys World")
  documentTable.pushText("Hello hello")
  documentTable.pushText("Hello guys guys hello hello guys hello")
```

#### And then we insert the search vector:
```scala
  documentTable.pushQuery("Hello Guys")
```

#### Execute the query:
```scala
  println(documentTable.result())
```

# Result:
```scala
 SearchVectorResult(1,0.2928932188134525)
 SearchVectorResult(2,0.7071067811865475)
 SearchVectorResult(3,0.2928932188134525)
 SearchVectorResult(4,0.4142135623730951)
 SearchVectorResult(5,0.6180339887498948)
 SearchVectorResult(6,0.9608279654492813)
```

