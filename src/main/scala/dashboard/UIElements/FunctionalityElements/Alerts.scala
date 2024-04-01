package dashboard.UIElements.FunctionalityElements

import dashboard.UI.stage
import dashboard.lib.SaveFiles.getDashboardNames
import javafx.scene.Node
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ChoiceBox, ColorPicker, ComboBox, TextField}
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color

import scala.:+

object Alerts:

  private val stockChoises = ObservableBuffer("Apple", "Nvidia", "Microsoft")
  private val years = ObservableBuffer.from((2000 to 2024).reverse)
  private val portfolioChoises = ObservableBuffer("Portfolio1")

  def getBarChartAlert =

    val companyChoiceBox = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head

    val colorPicker = new ColorPicker(Color.White)
    
    val alertContent = new VBox:
      children = Array(companyChoiceBox, colorPicker)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Bar Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox, colorPicker)

  def getXYChartAlert =
    
    val companyChoiceBox = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.head

    val colorPicker = new ColorPicker(Color.White)

    val alertContent = new VBox:
      children = Array(companyChoiceBox, colorPicker)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "XY Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, companyChoiceBox, colorPicker)

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

    val colorPicker1 = new ColorPicker(Color.White)

    val companyChoiceBox2 = new ChoiceBox[String]:
      items = stockChoises
      value = stockChoises.last

    val colorPicker2 = new ColorPicker(Color.White)

    val yearChoiceBox = new ChoiceBox[Int]:
      items = years
      value = years.head

    val alertContent = new VBox:
      children = Array(companyChoiceBox1, colorPicker1, companyChoiceBox2, colorPicker2, yearChoiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Scatter plot customization"
      headerText = "Choose company and year"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)



    (alert, companyChoiceBox1, companyChoiceBox2, yearChoiceBox)


  def getDashboardSavingAlert =
    val fileNameTextField = new TextField

    val alertContent = new VBox:
      children = Array(fileNameTextField)
      alignment = Pos.Center

    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Save dashboard"
      headerText = "Enter dashboard name"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, fileNameTextField)
  
  def getDashboardOpeningAlert =
    val dashboardNames = ObservableBuffer.from(getDashboardNames)

    // TODO: bind items to names of saved dashboards
    val dashboarChoiseBox = new ChoiceBox[String]:
      items = dashboardNames
    
    val alertContent = new VBox:
      children = Array(dashboarChoiseBox)
      alignment = Pos.Center

    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Open dashboard"
      headerText = "Choose dashboard to open"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    (alert, dashboarChoiseBox)