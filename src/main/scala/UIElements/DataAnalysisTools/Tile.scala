package UIElements.DataAnalysisTools

import scalafx.application.JFXApp3
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, Separator}
import scalafx.scene.layout.{BorderPane, Pane, VBox}

object Tile extends JFXApp3:
  // TODO: Implement Tile

  /*
  Ideas to implement:
  - value of portfolio (sum)
  - portfolio min and max values (min & max)
  - average return per month (average)
  - standard deviation of return per month
   */
  
  def getTile(stock: String): BorderPane =

    val tileCenter = new VBox:
      children = Array(Label("This"), Label("Is"), Label("Important"), Label("Information"))
      alignment = Pos.Center

    val tile = new BorderPane(
      tileCenter,
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical),
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical))
    
    tile
  
  
  override def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Testingg babyyy!"
      width = 500
      height = 500

    val center1 = Button("first")
    val center2 = Button("second")

    val tileCenter = new VBox:
      children = Array(Label("This"), Label("Is"), Label("Important"), Label("Information"))
      alignment = Pos.Center

    val tile = new BorderPane(
      tileCenter,
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical),
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical))

    val tile2 = new BorderPane(
      center2,
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical),
      Separator(Orientation.Horizontal),
      Separator(Orientation.Vertical))

    val root = new VBox:
      children = Array(tile, tile2)

    val scene = Scene(parent = root)
    stage.scene = scene
