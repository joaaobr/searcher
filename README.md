<h1> What is Searcher ?? </h1>

Searcher is a study project with the objective of using VSM (Vector Space Model) to search documents and reveal the similarity between the document and the query.

## Example:

#### The document table is a module used to store documents and transform them into lists of sentences.
```scala
  val tableDocuments = new TableDocuments()
```
#### Then we insert the documents into the table:

```scala
  tableDocuments.pushText("Hello World")
  tableDocuments.pushText("Hello Guys")
  tableDocuments.pushText("Hello Main")
  tableDocuments.pushText("Hello Man Guys World")
  tableDocuments.pushText("Hello hello")
  tableDocuments.pushText("Hello guys guys hello hello guys hello")
```

#### And then we insert the search vector:
```scala
  tableDocuments.pushQuery("Hello Guys")
```

#### Execute the query:
```scala
  println(tableDocuments.result())
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

# Run
```bash
sbt run
```

