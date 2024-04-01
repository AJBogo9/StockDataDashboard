package dashboard.lib

import javafx.scene.Node
import javafx.scene.chart.{BarChart, LineChart, PieChart, ScatterChart}
import javafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Orientation
import scalafx.scene.chart.XYChart
import scalafx.scene.control.Separator
import scalafx.scene.layout.BorderPane
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer
import scalafx.Includes.jfxVBox2sfx
import scalafx.scene.SceneIncludes.jfxVBox2sfx
import scalafx.scene.layout.LayoutIncludes.jfxVBox2sfx
import scalafx.Includes.jfxPane2sfx
import scalafx.scene.SceneIncludes.jfxPane2sfx
import scalafx.scene.layout.LayoutIncludes.jfxPane2sfx
import scalafx.Includes.jfxLabel2sfx
import scalafx.scene.SceneIncludes.jfxLabel2sfx
import scalafx.scene.control.ControlIncludes.jfxLabel2sfx
import scalafx.Includes.jfxLabeled2sfx
import scalafx.scene.SceneIncludes.jfxLabeled2sfx
import scalafx.scene.control.ControlIncludes.jfxLabeled2sfx

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Sorting.quickSort

object Utils:

  def xySeriesScatter(name: String, data: Seq[(Int, Double)]) =
    val dataInObservableBuffer = ObservableBuffer.from(
      data.map( (x, y) => XYChart.Data[Number, Number](x, y) )
    )
    XYChart.Series[Number, Number](name, dataInObservableBuffer)

  def xySeries(companyName: String, companyData: Seq[(String, Double)]) =
    val dataInObservableBuffer = ObservableBuffer.from(
      companyData.map((x, y) => XYChart.Data[String, Number](x, y))
    )
    XYChart.Series[String, Number](companyName, dataInObservableBuffer)

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
    
  


