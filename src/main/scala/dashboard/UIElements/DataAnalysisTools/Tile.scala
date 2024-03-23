package dashboard.UIElements.DataAnalysisTools

import dashboard.lib.Api.{getPortfolioData, getTimeSeries}
import dashboard.lib.Utils.borderedElement
import scalafx.application.JFXApp3
import scalafx.geometry.{Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, Separator}
import scalafx.scene.layout.{BorderPane, Pane, VBox}
import scalafx.scene.text.{Font, FontWeight}

import scala.:+
import scala.math.sqrt

object Tile:
  
  def getStockTile(stockName: String): BorderPane =
    val stockTimeSeries = getTimeSeries(stockName)
    val stockOpenValuesMap = stockTimeSeries.keys.map(date => date -> stockTimeSeries(date)("1. open")).toMap

    val maxValue = stockOpenValuesMap.maxBy(_._2)
    val minValue = stockOpenValuesMap.minBy(_._2)


    var index = 1
    val returnCount = stockOpenValuesMap.size - 1

    var returnValues = Array[Double]()
    val sortedDatesAndValues = stockOpenValuesMap.toSeq.sortBy(_._1)
    val values = sortedDatesAndValues.map( _._2 )
    while index < returnCount do
      returnValues = returnValues :+ (values(index) - values(index - 1))
      index += 1
    var sumOfReturns = returnValues.sum

    val meanReturn = sumOfReturns / returnCount


    // calculating stangard deviation
    val meanSubtracted = returnValues.map( value => value - meanReturn )
    val squared = meanSubtracted.map( value => value * value )
    val sumOfSquaredDeviations = squared.sum
    val sumDividedByAmountOfDatapoints = sumOfSquaredDeviations / meanSubtracted.length
    val standardDeviationReturn = sqrt(sumDividedByAmountOfDatapoints)
    
    val stockNameLabel = new Label(s"$stockName"):
      font = Font("System", FontWeight.ExtraBold, 15)

    val formatter = java.text.NumberFormat.getCurrencyInstance

    val stockTileCenter = new VBox:
      children = Array(
        stockNameLabel,
        Label(s"Min value: ${formatter.format(minValue._2)} (${minValue._1})"),
        Label(s"Max value: ${formatter.format(maxValue._2)} (${maxValue._1})"),
        Label(s"Return per month mean: ${formatter.format(meanReturn)}"),
        Label(s"Return standard deviation: ${formatter.format(standardDeviationReturn)}")
      )
      alignment = Pos.Center

    borderedElement(stockTileCenter)


  def getPortfolioTile(portfolioName: String): BorderPane =

    val portfolioData = getPortfolioData(portfolioName)
    val companyAndQuantityMap = portfolioData.keys.map(company => company -> portfolioData(company)("Quantity")).toMap


    var portfolioValue = 0.0
    for company <- companyAndQuantityMap.keys do
      val companyTimeSeries = getTimeSeries(company)
      val dateAndOpenValueMap = companyTimeSeries.keys.map(date => date -> companyTimeSeries(date)("1. open")).toMap

      val lastDate = dateAndOpenValueMap.max._1
      val quantity = companyAndQuantityMap(company).toDouble
      val stockValue = dateAndOpenValueMap(lastDate)

      portfolioValue += quantity * stockValue

    val portfolioNameLabel = new Label(s"$portfolioName"):
      font = Font("System", FontWeight.ExtraBold, 15)

    val formatter = java.text.NumberFormat.getCurrencyInstance

    val portfolioTileCenter = new VBox:
      children = Array(
        portfolioNameLabel,
        Label(s"Portfolio value: ${formatter.format(portfolioValue)}")
      )
      alignment = Pos.Center

    borderedElement(portfolioTileCenter)
    