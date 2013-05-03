package sudoku

import scala.io.Source

object OneLineDotParser extends Parser {
  def parse(fileName: String) = {
    val source = Source.fromFile(fileName)

    val lines = source.getLines

    val parsedLines = lines.map { s =>
      s.grouped(9).toIndexedSeq.map { l =>
        l.toIndexedSeq.map { c =>
          c match {
            case '.' => None
            case other => Some(other.asDigit)
          }
        }
      }
    }
    
    parsedLines.map(g => new Grid(g,9,9))
  }
}