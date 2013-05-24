package sudoku

import org.scalatest.FunSuite
import scala.io.Source
import Cells._

class CrazySudokuSuite extends FunSuite {
  val easySource = Source.fromFile("sudokuEasy.txt")

  val easyLines = easySource.getLines.toIndexedSeq

  val easyParsedLines = easyLines.grouped(10).map { l =>
    l.tail.map { s =>
      s.toIndexedSeq.map { c =>
        c.asDigit match {
          case 0 => None
          case other => Some(other)
        }
      }
    }
  }

  val easyGrids = easyParsedLines.map(g => new Grid(g, 3, 3)).toIndexedSeq

  var currentGrid = 1

  test("easy grids") {
    assert {
      easyGrids.par.forall { t =>
        val solved = SudokuSolver.solve(t)
//        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }

  val hardGrids = OneLineDotParser.parse("sudokuHard.txt").take(3)

  test("hard grids") {
    currentGrid = 1
    assert {
      hardGrids.forall { t =>
//        println("Solving " + currentGrid)
        val solved = SudokuSolver.solve(t)
//        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }
}