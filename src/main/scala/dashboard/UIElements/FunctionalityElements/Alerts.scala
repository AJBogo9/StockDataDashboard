package dashboard.UIElements.FunctionalityElements

import dashboard.UI.stage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ChoiceBox, ComboBox}
import scalafx.scene.layout.VBox

import scala.:+

object Alerts:

  private val stockChoises = ObservableBuffer("Apple", "Nvidia", "Microsoft")
  private val years = ObservableBuffer.from((2000 to 2024).reverse)
  private val portfolioChoises = ObservableBuffer("Portfolio1")

  def getBarChartAlert =

    val companyChoiceBox = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head
    
    val alertContent = new VBox:
      children = Array(companyChoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Bar Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox)

  def getXYChartAlert =
    
    val companyChoiceBox = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head

    val alertContent = new VBox:
      children = Array(companyChoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "XY Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox)

  def getPieChartAlert =

    val portfolioCoiceBox = new ChoiceBox[String]:
      items = portfolioChoises
      value = portfolioChoises.head

    val alertContent = new VBox:
      children = Array(portfolioCoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Pie chart customization"
      headerText = "Choose portfolio"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, portfolioCoiceBox)

  def getPortfolioTileAlert =

    val portfolioCoiceBox = new ChoiceBox[String]:
      items = portfolioChoises
      value = portfolioChoises.head

    val alertContent = new VBox:
      children = Array(portfolioCoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Portfolio tile customization"
      headerText = "Choose portfolio"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, portfolioCoiceBox)


  def getStockTileAlert =

    val companyChoiceBox = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head

    val alertContent = new VBox:
      children = Array(companyChoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Stock tile customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox)

  def getScatterPlotAlert =
    val companyChoiceBox1 = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head

    val companyChoiceBox2 = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.last

    val yearChoiceBox = new ChoiceBox[Int]:
      items = years
      value = years.head

    val alertContent = new VBox:
      children = Array(companyChoiceBox1, companyChoiceBox2, yearChoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Scatter plot customization"
      headerText = "Choose company and year"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox1, companyChoiceBox2, yearChoiceBox)