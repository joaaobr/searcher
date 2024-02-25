package com.searcher.vsm.document

import java.nio.file.*;

class DocumentFile(file: String) {
  def read(): Seq[String] = {
    val stream = Files.lines(Paths.get(file))
    stream
    .filter(x => x.length >= 2)
    .map(x => Document.clear(x))
    .reduce((x, y) => x ++ y)
    .get()
  }
}