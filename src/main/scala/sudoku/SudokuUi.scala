//package solver
//
//import swing._
//import java.awt.Color
//import scala.swing.event.ButtonClicked
//import scala.collection.mutable
//import scala.io.Source
//
//object SudokuUi extends SimpleSwingApplication {
//  val textBoxes = mutable.IndexedSeq.fill(9, 9)(new TextField {
//    columns = 1
//  })
//  val source = Source.fromFile("sudokuLevel2")
//  val lines = mutable.IndexedSeq(source.getLines.toIndexedSeq).flatten
//  val split = lines.map(s => mutable.IndexedSeq(s.split(",")).flatten)
//  
//  
//  for (x <- 0 until 9; y <- 0 until 9) {
//    val s = split(y)(x)
//    if (s == "_") textBoxes(y)(x).text = ""
//    else textBoxes(y)(x).text = s
//  }
//
//  
//  def top = new MainFrame {
//    val doneButton = new Button { text = "Solve" }
//    title = "First Swing App"
//    contents = new BoxPanel(Orientation.Vertical) {
//      for (a <- 0 until 3) {
//        contents += new BoxPanel(Orientation.Horizontal) {
//          for (b <- 0 until 3) {
//            contents += new BoxPanel(Orientation.Vertical) {
//              border = Swing.LineBorder(Color.BLUE)
//              for (c <- 0 until 3) {
//                contents += new BoxPanel(Orientation.Horizontal) {
//                  for (d <- 0 until 3) {
//                    val x = 3*b + d
//                    val y = 3*a + c
//                    contents += textBoxes(y)(x)
//                  }
//                }
//              }
//            }
//          }
//        }
//      }
//
//      contents += doneButton
//
//      listenTo(doneButton)
//      reactions += {
//        case ButtonClicked(doneButton) => {
//          val mapped = textBoxes.map{_.map{tb =>
//            val text = tb.text
//            if (text.isEmpty) {
//              None
//            } else {
//              Some(text.toInt)
//            }
//          }}
//          val mappedCells = mapped.map{_.map{n =>
//            new Cell(n)
//          }}
//          
//          var solved = NewSudokuSolver.solveLoop(mappedCells)
//          
//          
//          for (x <- 0 until 9; y <- 0 until 9) {
//            val valueToAdd = solved(y)(x).value
//            if (valueToAdd.isDefined) {
//              textBoxes(y)(x).text = valueToAdd.get.toString
//            }
//          }
//        }
//      }
//    }
//  }
//}