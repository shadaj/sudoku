package sudoku

import scala.annotation.tailrec

object SudokuSolver {
  @tailrec
  def fillInKnown(data: Grid): Grid = {
    val optionCoordinates = (0 until data.completeSides).zip(0 until data.completeSides).find(t => data.possibleValues(t._1, t._2) == 1)
    
    optionCoordinates match {
      case Some(coordinates) => {
        val possibilities = data.possibleValues(coordinates._1, coordinates._2)
        
        fillInKnown(data.updated(coordinates._1, coordinates._2, Some(possibilities.head)))
      }
      
      case None => data
    }
  }

  def canFillInKnown(data: Grid): Boolean = {
    (0 until data.completeSides).exists { y =>
      (0 until data.completeSides).exists { x =>
        val possibilities = data.possibleValues(x, y)
        possibilities.length == 1
      }
    }
  }

  def guess(data: Grid): Seq[Grid] = {
    val allCoordinates = for (x <- (0 until data.completeSides); y <- (0 until data.completeSides)) yield (x, y)
    val emptyCellOption = allCoordinates.find { case (x, y) => !data(x, y).isDefined }
    val (x, y) = emptyCellOption.get //There will always be a value since a puzzle with all values filled in is either incorrect or solved
    val possibilities = data.possibleValues(x, y)

    possibilities.map { p =>
      data.updated(x, y, Some(p))
    }
  }

  def solve(data: Grid): Option[Grid] = {
    if (!data.correct) {
      None
    } else {
      val filledIn = fillInKnown(data)
      if (filledIn.solved) {
        Some(filledIn)
      } else {
        val optionGrid = guess(filledIn).map(solve).find(_.isDefined)
        optionGrid match {
          case Some(grid) => grid
          case None => None
        }
      }
    }
  }
}