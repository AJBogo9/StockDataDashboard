package dashboard.lib

import ujson.Value

import java.io.FileWriter
import java.nio.file.{Files, Paths}
import java.time.LocalDate
import scala.collection.mutable
import scala.collection.mutable.Map
import scala.io.Source

object Api:

  // private val filesRefreshedToday: Boolean = LocalDate.parse(getMetaData("Apple")("3. Last Refreshed")) == LocalDate.now().minusDays(1)
  private val apiKey: String =
    val apiKeyFile = "apiKey.txt"
    val source = Source.fromFile(apiKeyFile)
    val key = source.getLines().mkString
    source.close()
    key

  private val timeSeriesDstFolder = "src/main/scala/dashboard/data/timeSeries"

  /**
   * Does an API call to https://www.alphavantage.co/documentation/ and saves the data
   * into a file for later use
   * @param function The program works only with "TIME_SERIES_MONTHLY" at the moment
   * @param companyName Company name starting with a capital letter
   * @return
   */
  def getDataFromAlphavantageAndSave(function: String, companyName: String): String =
    // if !filesRefreshedToday then // does not use up API calls if data is up to date
    val symbol = companySymbol(companyName)

    // get data from api
    val urlMonthly = s"https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=$symbol&apikey=$apiKey"
    val html = Source.fromURL(urlMonthly)
    val text = html.mkString

    // Create directory
    Files.createDirectories(Paths.get(timeSeriesDstFolder))

    // Write to file
    val file = s"$timeSeriesDstFolder/${function}_$symbol.json"
    val fw = new FileWriter(file, false)
    fw.write(text)
    fw.close()

    text


  def readTextFromFile(src: String): String =
    val bufferedSource = Source.fromFile(src)
    val text = bufferedSource.mkString
    bufferedSource.close()
    text

  def getMetaData(company: String): mutable.Map[String, String] =
    val function = "TIME_SERIES_MONTHLY"
    val symbol = companySymbol(company)
    val src = s"$timeSeriesDstFolder/${function}_${symbol}.json"

    val rawText = readTextFromFile(src)
    val metaData: Value = ujson.read(rawText).obj("Meta Data")
    val usableMetaData = metaData.obj
    val ret: Map[String, String] = Map()
    for key <- usableMetaData.keys do
      ret += key -> usableMetaData(key).str
    ret

  def getTimeSeries(company: String): mutable.Map[String, mutable.Map[String, Double]] =
    val function = "TIME_SERIES_MONTHLY"
    val symbol = companySymbol(company)
    val src = s"$timeSeriesDstFolder/${function}_$symbol.json"

    val rawText = readTextFromFile(src)

    val timeSeries: Map[String, Value] = ujson.read(rawText).obj("Monthly Time Series").obj
    var retMap: Map[String, Map[String, Double]] = Map()
    for date <- timeSeries.keys do
      // converts Value type to Double
      val numbers: Map[String, Value] = timeSeries(date).obj
      val newNumbers: Map[String, Double] = Map()
      for key <- numbers.keys do
        newNumbers += key -> numbers(key).str.toDouble
      retMap += date -> newNumbers

    retMap

  def getPortfolioData(portfolioName: String): Map[String, Map[String, String]] =
    val portfolioSrc = s"src/main/scala/dashboard/data/portfolios/$portfolioName.json"
    val rawText = readTextFromFile(portfolioSrc)
    val data = ujson.read(rawText).obj
    var ret: Map[String, Map[String, String]]= Map()
    for key <- data.keys do
      val dataObj: Map[String, Value] = data(key).obj
      val mapped: Map[String, String] = Map()
      for key <- dataObj.keys do
        mapped += key -> dataObj(key).str
      ret += key -> mapped
    ret

  def companySymbol(company: String): String =
    val symbols = Map("Apple" -> "AAPL", "Nvidia" -> "NVDA", "Microsoft" -> "MSFT")
    symbols(company)