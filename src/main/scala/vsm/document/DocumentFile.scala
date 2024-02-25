package com.searcher.vsm.document

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.BufferedSource
import java.io.BufferedReader
import java.io.FileReader
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

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