package dashboard.UIElements.DataAnalysisTools

import dashboard.lib.Api.getTimeSeries
import dashboard.lib.Utils.{borderedElement, xySeries}
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}

import scala.util.Sorting.quickSort

object VolymeBarChart:
  def getVolymeBarChart(company: String, color: String) =
    val ApiData = getTimeSeries(company)
    val dates = ApiData.keys.toArray
    quickSort(dates)
    var datesAndVolyme: Seq[(String, Double)] = Seq()
    for date <- dates do
      datesAndVolyme = datesAndVolyme :+ (date, ApiData(date)("5. volume"))

    val chart = new BarChart(CategoryAxis("Date"), NumberAxis("Volyme")):
      title = s"$company trading volyme"
      legendSide = Side.Right
      data = ObservableBuffer(
        xySeries(company, datesAndVolyme)
      )

    chart.getStylesheets().setAll(
      s"""
                        data:text/css,
                        .default-color0.chart-bar { -fx-background-color: #$color; }
                        """
    )

    borderedElement(chart)