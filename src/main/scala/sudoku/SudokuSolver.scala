package sudoku

import scala.annotation.tailrec
import Cells._

object SudokuSolver {
  @tailrec
  def fill(data: Grid): Grid = {
    val possibleCoordinates = for (x <- 0 until data.size; y <- 0 until data.size) yield (x, y)
    
    val coordinatesToFill = possibleCoordinates.view.map { case (x, y) => (x, y, data.possibleValues(x, y)) }.collectFirst {
      case (x, y, possibleValues) if (possibleValues.length == 1) => (x, y, possibleValues.head)
    }

    coordinatesToFill match {
      case Some((x, y, value)) => {
        fill(data.updated(x, y, value))
      }
      case None => data
    }
  }

  def guess(data: Grid): Seq[Grid] = {
    val allCoordinates = for (x <- 0 until data.size; y <- 0 until data.size) yield (x, y)
    
    //There will always be a value since a puzzle with all values filled in is either incorrect or solved
    val Some((x,y)) = allCoordinates.collectFirst { case c@(x, y) if (!data(x, y).isDefined) => c }
    
    val possibilities = data.possibleValues(x, y)

    possibilities.map { p =>
      data.updated(x, y, p)
    }
  }

  def solve(data: Grid): Option[Grid] = {
    val filledIn = fill(data)
    if (filledIn.solved) {
      Some(filledIn)
    } else {
      guess(filledIn).view.map(solve).collectFirst{case Some(grid) => grid}
    }
  }
}