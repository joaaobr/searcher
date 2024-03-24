import com.searcher.vsm._
import com.searcher.vsm.document.DocumentTable
import com.searcher.vsm.document.index._

@main def hello(): Unit =
  val documentTable = new DocumentTable()
  
  documentTable.pushText("Hello World")
  documentTable.pushText("Hello Guys")
  documentTable.pushText("Hello Main")
  documentTable.pushText("Hello Man Guys World")
  documentTable.pushText("Hello hello")
  documentTable.pushText("Hello guys guys hello hello guys hello")

  documentTable.pushQuery("Hello Guys")

  // DocumentTable.result().show()
  documentTable.resultWithIndex().show()