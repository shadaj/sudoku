package sudoku

object Cells {
	type Cell = Option[Int]
	implicit def intToCell(num: Int): Cell = Some(num)
}