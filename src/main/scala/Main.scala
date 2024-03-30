import com.searcher.vsm._
import com.searcher.vsm.document.DocumentTable
import com.searcher.vsm.document.index._
import vsm.Config
import vsm.ConfigSets

@main def hello(): Unit =
  val settings = new Config()

  settings.setInvertedIndex(false)
  settings.setIndividualComparisonVector(true)

  val documentTable = new DocumentTable(settings)
  
  documentTable.pushText("Hello World")
  documentTable.pushText("Hello Guys")
  documentTable.pushText("Hello Main")
  documentTable.pushText("Hello Man Guys World")
  documentTable.pushText("Hello hello")
  documentTable.pushText("Hello guys guys hello hello guys hello")
  documentTable.pushText("TESTER WIWIW")

  documentTable.pushQuery("TESTER HELLO")

  documentTable.result().showOrdened()
