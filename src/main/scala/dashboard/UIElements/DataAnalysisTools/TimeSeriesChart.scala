package dashboard.UIElements.DataAnalysisTools

import dashboard.UIElements.FunctionalityElements.RightSplit.componentWidthAndHeigth
import dashboard.lib.Api.getTimeSeries
import dashboard.lib.Utils.xySeries
import geny.Generator.from
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.*
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer
import javafx.scene.paint.Color



object TimeSeriesChart:
  def getTimeSeriesChart(company: String, color: String) =
    val timeSeries = getTimeSeries(company)
    var dateValuePairs = timeSeries.map(date => (date._1, date._2("1. open"))).toSeq.sortBy(_._1)

    val (chartWidth, chartHeigth) = componentWidthAndHeigth

    val chart = new LineChart(CategoryAxis("Date"), NumberAxis("Price")):
      title = s"$company stock value"
      legendSide = Side.Bottom
      prefWidth = chartWidth
      prefHeight = chartHeigth
      data = ObservableBuffer(
        xySeries(company, dateValuePairs)
      )

    chart.getStylesheets().setAll(
                    s"""
                    data:text/css,
                    .default-color0.chart-line-symbol { -fx-background-color: #$color, white; }
                    .default-color0.chart-series-line { -fx-stroke: #$color; }
                    """
    )


    chart
