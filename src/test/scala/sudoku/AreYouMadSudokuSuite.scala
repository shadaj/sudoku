package sudoku

import org.scalatest.FunSuite
import scala.io.Source

class AreYouMadSudokuSuite extends FunSuite {
  val superHardSource = Source.fromFile("sudoku17.txt")

  val superHardLines = superHardSource.getLines

  val superHardParsedLines = superHardLines.map { s =>
    s.grouped(9).toIndexedSeq.map { l =>
      l.toIndexedSeq.map { c =>
        c match {
          case '0' => None
          case other => Some(other.asDigit)
        }
      }
    }
  }

  val superHardGrids = superHardParsedLines.map(new Grid(_, 3, 3))

  var currentGrid = 0
  
  test("super hard grids") {
    currentGrid = 1
    assert {
      superHardGrids.take(100).forall { t =>
//        println("Solving " + currentGrid)
        val solved = SudokuSolver.solve(t)
//        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }
}