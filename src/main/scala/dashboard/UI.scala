package dashboard

import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.Tile.{getPortfolioTile, getStockTile}
import dashboard.UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import dashboard.UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import dashboard.UIElements.DataAnalysisTools.VolymeBarChart.getVolymeBarChart
import dashboard.UIElements.DataAnalysisTools.Tile.*
import dashboard.UIElements.FunctionalityElements.Alerts.*
import dashboard.UIElements.FunctionalityElements.LeftSplit.getLeftSplit
import dashboard.UIElements.FunctionalityElements.MenuElement.*
import dashboard.UIElements.FunctionalityElements.RightSplit.getRightSplit
import dashboard.UIElements.FunctionalityElements.ToolBar.*
import dashboard.lib.Api.*
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ChoiceBox, Label, ScrollPane, SplitPane}
import scalafx.scene.layout.{BorderPane, HBox, Pane, VBox}
import scalafx.scene.text.{Font, FontWeight}

object UI extends JFXApp3:

  val charts = new VBox:
    children = Array[BorderPane]()
    alignment = Pos.Center

  def start() =


    val nvidiaTile = getStockTile("Nvidia")
    val appleTile = getStockTile("Apple")
    val portfolioTile = getPortfolioTile("portfolio1")

    val rightSplitWithScrollPane = new ScrollPane:
      content = charts





    val menu = getMenuElement
    val toolBar = getToolBarElement

    val leftSplit = getLeftSplit
    val rightSplit = getRightSplit
    val splitPane = new SplitPane:
      items ++= Seq(leftSplit, rightSplit)
      dividerPositions = 0.3

    val mainVBox = new VBox:
      children = Array(menu, toolBar, splitPane)



    stage = new JFXApp3.PrimaryStage:
      title = "Personal Portfolio Dashboard"
      scene = new Scene(700, 500):
        root = mainVBox











    refreshButton.onAction = (event) =>
      println("The button is working!!")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Apple")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Microsoft")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Nvidia")