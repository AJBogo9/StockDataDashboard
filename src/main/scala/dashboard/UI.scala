package dashboard

import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.Tile.{getPortfolioTile, getStockTile}
import dashboard.UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import dashboard.UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import dashboard.UIElements.DataAnalysisTools.VolymeBarChart.getVolymeBarChart
import dashboard.UIElements.DataAnalysisTools.Tile.*
import dashboard.UIElements.FunctionalityElements.Alerts.*
import dashboard.UIElements.FunctionalityElements.MenuElement.*
import dashboard.UIElements.FunctionalityElements.ToolBar.*
import dashboard.lib.Api.*
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ChoiceBox, Label, ScrollPane, SplitPane}
import scalafx.scene.layout.VBox

object UI extends JFXApp3:

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Personal Portfolio Dashboard"

    val menu = getMenuElement
    val toolBar = getToolBarElement

    /*
    // Charts
    val pieChart = getPieChart("portfolio1")
    val volymeBarChart = getVolymeBarChart("Nvidia")
    val scatterPlot = getScatterPlot(Array("Nvidia", "Apple"), 2023)
    val lineChart = getTimeSeriesChart("Nvidia")

     */
    val nvidiaTile = getStockTile("Nvidia")
    val appleTile = getStockTile("Apple")
    val portfolioTile = getPortfolioTile("portfolio1")
    // val scatterPlot = getScatterPlot(Array("Apple"), 2022)

    // pieChart, volymeBarChart, scatterPlot, lineChart, tile

    val charts = new VBox:
      children = Array(nvidiaTile, appleTile, portfolioTile)
      alignment = Pos.Center

    val rightSplit = new ScrollPane:
      content = charts

    val leftSplit = new VBox:
      children = Array(Label("Hidden components:"), Button("More"), Label("Components"))

    val splitPane = new SplitPane:
      items ++= Seq(leftSplit, rightSplit)

    val root = new VBox:
      children = Array(menu, toolBar, splitPane)


    stage.scene = Scene(root, 700, 500)


    // ALERTS

    /*
    val choices = ObservableBuffer("Apple", "Nvidia", "Microsoft")

    val choiceBox = new ChoiceBox[String]:
      items = choices
      value = choices.head

    val alertContent = new VBox:
      children = Array(choiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

     */



    val (barChartAlert, barChartChoiceBox) = getBarChartAlert
    barChartMenuItem.onAction = (event) =>
      val result = barChartAlert.showAndWait()
      val company = barChartChoiceBox.value.value
      result match
        case Some(_) => charts.children += getVolymeBarChart(company)
        case None =>

    val (xyChartAlert, xyChartChoiceBox) = getXYChartAlert
    xyChartMenuItem.onAction = (event) =>
      val result = xyChartAlert.showAndWait()
      val company = xyChartChoiceBox.value.value
      result match
        case Some(_) => charts.children += getTimeSeriesChart(company)
        case None =>

    val (pieChartAlert, portfolioChoiseBoxPieChart) = getPieChartAlert
    pieChartMenuItem.onAction = (event) =>
      val result = pieChartAlert.showAndWait()
      val portfolio = portfolioChoiseBoxPieChart.value.value
      result match
        case Some(_) => charts.children += getPieChart(portfolio)
        case None =>

    val (portfolioTileAlert, portfolioChoiceBoxTile) = getPortfolioTileAlert
    portfolioTileMenuItem.onAction = (event) =>
      val result = portfolioTileAlert.showAndWait()
      val portfolio = portfolioChoiceBoxTile.value.value
      result match
        case Some(_) => charts.children += getPortfolioTile(portfolio)
        case None =>

    val (stockTileAlert, stockTileChoiseBox) = getStockTileAlert
    stockTileMenuItem.onAction = (event) =>
      val result = stockTileAlert.showAndWait()
      val company = stockTileChoiseBox.value.value
      result match
        case Some(_) => charts.children += getStockTile(company)
        case None =>

    val (scatterPlotAlert, scatterPlotChoiceBox1, scatterPlotChoiceBox2, yearChoiceBox) =
      getScatterPlotAlert
    scatterPlotMenuItem.onAction = (event) =>
      val result = scatterPlotAlert.showAndWait()
      val companies = Array(scatterPlotChoiceBox1.value.value, scatterPlotChoiceBox2.value.value)
      val year = yearChoiceBox.value.value
      result match
        case Some(_) => charts.children += getScatterPlot(companies, year)
        case None =>

    /*
    // getScatterPlot(Array("Nvidia", "Apple"), 2023)
    val scatterPlotAlert = getScatterPlotAlert
    scatterPlotMenuItem.onAction = (event) =>
      val result = scatterPlotAlert.showAndWait()
      val companies = scatterPlotChoiceBox.value.value
      val year = yearChoiceBox.value.value
      result match
        case Some(_) => charts.children += getScatterPlot(companies, year)
        case None =>
    
    */



    refreshButton.onAction = (event) =>
      println("The button is working!!")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Apple")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Microsoft")
      getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Nvidia")