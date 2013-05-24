package sudoku

import Cells._
import collection.mutable

class Grid(data: IndexedSeq[IndexedSeq[Cell]], boxWidth: Int, boxHeight: Int) {
  val size = boxWidth * boxHeight
  val boxesInWidth = size / boxWidth
  val boxesInHeight = size / boxHeight

  def updated(x: Int, y: Int, value: Cell) = {
    new Grid(data.updated(y, data(y).updated(x, value)), boxWidth, boxHeight)
  }

  def apply(x: Int, y: Int) = {
    data(y)(x)
  }

  def column(x: Int): IndexedSeq[Cell] = {
    data.map(_(x))
  }

  def row(y: Int): IndexedSeq[Cell] = {
    data(y)
  }

  private[sudoku] def block(blockX: Int, blockY: Int): IndexedSeq[Cell] = {
    val startX = blockX * boxWidth
    val startY = blockY * boxHeight
    (startY until startY + boxHeight).flatMap(rowNum => (startX until startX + boxWidth).map(data(rowNum)(_)))
  }

  def blockFor(x: Int, y: Int): IndexedSeq[Cell] = {
    val blockX = x / boxWidth
    val blockY = y / boxHeight
    block(blockX, blockY)
  }

  def possibleValues(x: Int, y: Int) = {
    this(x, y) match {
      case Some(_) => List.empty
      case None => {
        val block = blockFor(x, y)
        val rowNums = this row y
        val columnNums = this column x
        (1 to size).filter(n =>
          !block.contains(Some(n)) &&
            !rowNums.contains(Some(n)) &&
            !columnNums.contains(Some(n)))
      }
    }
  }

  private def check(filtering: Cell => Boolean) = {
    def helper(values: IndexedSeq[Cell]) = {
      values.forall {
        _ match {
          case Some(e) => e >= 1 && e <= size
          case _ => true
        }
      } && values.filter(filtering).length == values.filter(filtering).distinct.length
    }

    val blocksChecked = (0 until boxesInWidth).forall { x =>
      (0 until boxesInHeight).forall { y =>
        helper(block(x, y))
      }
    }

    val rowsAndColumnsChecked = (0 until size).forall {
      i => helper(column(i)) && helper(row(i))
    }

    blocksChecked && rowsAndColumnsChecked
  }

  def solved: Boolean = check(_ => true)

  def correct: Boolean = check(_.isDefined)

  override def toString = {
    data.grouped(3).map { g =>
      g.map { l =>
        l.grouped(3).map { bg =>
          bg.map { c =>
            c match {
              case Some(value) => value
              case _ => "."
            }
          }.mkString(" ")
        }.mkString("|")
      }.mkString("\n")
    }.mkString("\n-----+-----+-----\n")
  }
}