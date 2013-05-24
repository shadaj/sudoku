package sudoku

import language.implicitConversions

object Cells {
	type Cell = Option[Int]
	implicit def intToCell(num: Int): Cell = Some(num)
}