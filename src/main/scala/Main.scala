import com.searcher.vsm._

@main def hello(): Unit =
  val DocumentTable = new DocumentTable()

  DocumentTable.pushText("Hello World")
  DocumentTable.pushText("Hello Guys")
  DocumentTable.pushText("Hello Main")
  DocumentTable.pushText("Hello Man Guys World")
  DocumentTable.pushText("Hello hello")
  DocumentTable.pushText("Hello guys guys hello hello guys hello")

  DocumentTable.pushQuery("Hello Guys")

  println(DocumentTable.result().all())
  
