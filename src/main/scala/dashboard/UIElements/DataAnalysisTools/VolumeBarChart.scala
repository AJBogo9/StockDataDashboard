package dashboard.UIElements.DataAnalysisTools

import dashboard.UIElements.FunctionalityElements.RightSplit.componentWidthAndHeigth
import dashboard.lib.Api.getTimeSeries
import dashboard.lib.Utils.xySeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}

import scala.util.Sorting.quickSort

object VolumeBarChart:
  def getVolumeBarChart(company: String, color: String) =
    val ApiData = getTimeSeries(company)
    val dates = ApiData.keys.toArray
    quickSort(dates)
    var datesAndVolume: Seq[(String, Double)] = Seq()
    for date <- dates do
      datesAndVolume = datesAndVolume :+ (date, ApiData(date)("5. volume"))


    val (chartWidth, chartHeigth) = componentWidthAndHeigth

    val chart = new BarChart(CategoryAxis("Date"), NumberAxis("Volume")):
      title = s"$company trading volume"
      legendSide = Side.Bottom
      prefWidth = chartWidth
      prefHeight = chartHeigth
      data = ObservableBuffer(
        xySeries(company, datesAndVolume)
      )

    chart.getStylesheets().setAll(
      s"""
                        data:text/css,
                        .default-color0.chart-bar { -fx-background-color: #$color; }
                        """
    )

    chart