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
        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }
  
  val hardSource = Source.fromFile("sudokuHard.txt")
  
  val hardLines = hardSource.getLines
  
  val hardParsedLines = hardLines.map { s =>
    s.grouped(9).toIndexedSeq.map { l =>
      l.toIndexedSeq.map { c =>
        c match {
          case '.' => None
          case other => Some(other.asDigit)
        }
      }
    }
  }
  
  val hardGrids = hardParsedLines.map(new Grid(_, 3, 3)).toIndexedSeq
  
  test("hard grids") {
    currentGrid = 1
    assert {
      hardGrids.forall { t =>
        println("Solving " + currentGrid)
        val solved = SudokuSolver.solve(t)
        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }
  
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
  
  val superHardGrids = superHardParsedLines.map(new Grid(_, 3, 3)).toIndexedSeq
  
  test("super hard grids") {
    currentGrid = 1
    assert {
      superHardGrids.forall { t =>
        println(t)
        println("Solving " + currentGrid)
        val solved = SudokuSolver.solve(t)
        println("Solved " + currentGrid)

        currentGrid += 1

        solved.get.solved
      }
    }
  }
}