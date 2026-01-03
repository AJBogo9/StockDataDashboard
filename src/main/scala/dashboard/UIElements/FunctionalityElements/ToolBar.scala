package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.FunctionalityElements.RightSplit.removeSelectedComponents
import scalafx.scene.control.{Button, Separator, ToggleButton, ToolBar, Alert}
import scalafx.scene.control.Alert.AlertType
import dashboard.UIElements.FunctionalityElements.LeftSplit.hideComponent
import dashboard.fileManagement.Api.SaveApiData.getDataFromAlphavantageAndSave
import scalafx.application.Platform

object ToolBar:
  val (
    selectButton,
    hideButton,
    removeButton,
    refreshButton
  ) = (
    ToggleButton("Select"),
    Button("Hide"),
    Button("Remove"),
    Button("Refresh")
  )
  
  hideButton.onAction = (event) =>
    val removedComponents = removeSelectedComponents()
    removedComponents.foreach(hideComponent)
    
  removeButton.onAction = (event) =>
    removeSelectedComponents()
    
  refreshButton.onAction = (event) =>
    // Disable button during refresh
    refreshButton.disable = true
    refreshButton.text = "Refreshing..."
    
    // Run API calls in background thread to avoid freezing UI
    new Thread(() => {
      try {
        val companies = Array("Apple", "Microsoft", "Nvidia")
        
        for ((company, index) <- companies.zipWithIndex) {
          Platform.runLater {
            refreshButton.text = s"Refreshing $company..."
          }
          
          getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", company)
          
          // Wait between requests (Alpha Vantage free tier rate limit)
          // Only wait if not the last company
          if (index < companies.length - 1) {
            Thread.sleep(15000) // 15 seconds between requests
          }
        }
        
        // Success - show alert and re-enable button
        Platform.runLater {
          refreshButton.text = "Refresh"
          refreshButton.disable = false
          
          new Alert(AlertType.Information) {
            title = "Data Refreshed"
            headerText = "Stock data updated successfully"
            contentText = "All stock data has been refreshed from Alpha Vantage."
          }.showAndWait()
        }
        
      } catch {
        case e: Exception =>
          // Error handling
          Platform.runLater {
            refreshButton.text = "Refresh"
            refreshButton.disable = false
            
            new Alert(AlertType.Error) {
              title = "Refresh Failed"
              headerText = "Failed to refresh stock data"
              contentText = s"Error: ${e.getMessage}\n\nPlease check your API key and internet connection."
            }.showAndWait()
          }
      }
    }).start()
    
  def getToolBarElement: ToolBar =
    val toolBar = new ToolBar:
      items = Array(
        selectButton,
        new Separator,
        hideButton,
        removeButton,
        new Separator,
        refreshButton
      )
    toolBar