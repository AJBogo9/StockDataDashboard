package Charts

import Charts.BarChartDemo.stage
import lib.Api.getTimeSeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}

import scala.util.Sorting.quickSort

object BarChartDemo extends JFXApp3 {
  override def start(): Unit = {
    val ApiData = getTimeSeries("Nvidia")
    val dates = ApiData.keys.toArray
    quickSort(dates)
    var datesAndVolyme: Seq[(String, Double)] = Seq()
    for date <- dates do
      datesAndVolyme = datesAndVolyme :+ (date, ApiData(date)("5. volume"))


    stage = new JFXApp3.PrimaryStage {
      title = "BarChart"
      scene = new Scene {
        root = new BarChart(CategoryAxis("Date"), NumberAxis("Volyme")) {
          title = "Bar Chart"
          legendSide = Side.Right
          data = ObservableBuffer(
            xySeries("NVDA", datesAndVolyme.map(_._2.toInt))
          )
        }
      }
    }
    def xySeries(name: String, data: Seq[Int]) = {
      val series = dates zip data
      XYChart.Series[String, Number](
        name,
        ObservableBuffer.from(
          series.map({ 
            case (x, y) => XYChart.Data[String, Number](x, y) 
          }))
      )
    }
  }
}