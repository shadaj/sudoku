package sudoku

trait Parser {
  def parse(fileName: String): Iterator[Grid]
}