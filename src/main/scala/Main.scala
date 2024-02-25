import com.searcher.vsm._
import com.searcher.vsm.document.DocumentTable

@main def hello(): Unit =
  val DocumentTable = new DocumentTable()
  
  DocumentTable.pushText("Hello World")
  DocumentTable.pushFile("file-test")
  DocumentTable.pushText("HèllÔ, Guys")
  DocumentTable.pushText("Hello Main")
  DocumentTable.pushText("HÉllo Man Guys World")
  DocumentTable.pushText("Hello hello")
  DocumentTable.pushText("Hello guys guys hello hello guys hello")

  DocumentTable.pushQuery("Hello Guys")

  DocumentTable.result().show()
  DocumentTable.resultComparisonVectors().show()
  
