import com.searcher.vsm._

@main def hello(): Unit =
  val tableDocuments = new TableDocuments()

  tableDocuments.pushText("Hello World")
  tableDocuments.pushText("Hello Guys")
  tableDocuments.pushText("Hello Main")
  tableDocuments.pushText("Hello Man Guys World")
  tableDocuments.pushText("Hello hello")
  tableDocuments.pushText("Hello guys guys hello hello guys hello")

  tableDocuments.pushQuery("Hello Guys")

  println(tableDocuments.result())
  
