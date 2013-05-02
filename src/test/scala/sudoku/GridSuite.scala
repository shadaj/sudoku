package sudoku

import org.scalatest.FunSuite
import io.Source

trait SudokuPuzzles {
  def loadSudoku(fileName: String) = {
    val sudoku1Source = Source.fromFile(fileName)
    val commaSplit = sudoku1Source.getLines.map {line =>
      line.toIndexedSeq
    }.toIndexedSeq
    val grid = new Grid(commaSplit.map(_.map(t => if (t == ' ') None else Some(t.asDigit))), 3, 3)
    if (!grid.correct) {
      throw new IllegalArgumentException("Bad Grid")
    } else {
      grid
    }
  }
  
  val sudoku1 = loadSudoku("sudokuLevel1")
  
  val sudoku2 = loadSudoku("sudokuLevel2")
  
  val sudoku3 = loadSudoku("sudokuLevel3")
  
  val sudoku4 = loadSudoku("sudokuLevel4")
}

class GridSuite extends FunSuite with SudokuPuzzles {
	test("9 in a row") {
	  assert((0 until 9).forall(sudoku1.row(_).length == 9))
	}
    
    test("9 in a box") {
      assert(sudoku1.block(2, 2).length === 9)
    }
    
    test("sudokuLevel1") {
      assert(SudokuSolver.solve(sudoku1).get.solved)
    }
    
    test("sudokuLevel2") {
      assert(SudokuSolver.solve(sudoku2).get.solved)
    }
    
    test("sudokuLevel3") {
      assert(SudokuSolver.solve(sudoku3).get.solved)
    }
    
    test ("sudokuLevel4") {
      assert(SudokuSolver.solve(sudoku4).get.solved)
    }
}