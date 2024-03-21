package lib

import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.XYChart

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Sorting.quickSort

object Utils:


  /*
  def xySeries[T](name: String, data: Seq[(T, Double)]) =
    XYChart.Series[T, Number](
      name,
      ObservableBuffer.from(
        data.map({ case (x, y) => XYChart.Data[T, Number](x, y) }))
    )
   */

  def xySeriesScatter(name: String, data: Seq[(Int, Double)]) =
    XYChart.Series[Number, Number](
      name,
      ObservableBuffer.from(
        data.map({ case (x, y) => XYChart.Data[Number, Number](x, y) }))
    )

  def xySeries(name: String, data: Seq[(String, Double)]) =
    XYChart.Series[String, Number](
      name,
      ObservableBuffer.from(
        data.map({ case (x, y) => XYChart.Data[String, Number](x, y) }))
    )

  private def parseDate(dateString: String): LocalDate =
    LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  
  private def formatDate(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
  
  def getFirstDaysOfMonths(dates: List[String]): Array[String] = 
    val localDates = dates.map(parseDate)
    val firstDaysOfMonths = localDates.groupBy(date => (date.getYear, date.getMonthValue))
      .mapValues(_.minBy(_.getDayOfMonth))
      .values.toArray
    val ret = firstDaysOfMonths.map(formatDate)
    quickSort(ret)
    ret

  def getYearData(data: Array[(String, Double)], year: Int): Array[(String, Double)] =
    data.filter( (dateStr, _) => LocalDate.parse(dateStr).getYear == year )

