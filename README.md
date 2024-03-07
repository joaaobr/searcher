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
  documentTable.result().show()
```

# Result:
```scala
  Id  Similarity
  1   0.6666666666666667
  2   1.0000000000000002
  3   0.6666666666666667
  4   0.8660254037844387
  5   0.8164965809277259
  6   0.8082903768654761
```

## Running Test:
```bash
  sbt test
```