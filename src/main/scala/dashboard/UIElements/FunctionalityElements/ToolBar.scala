package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.FunctionalityElements.RightSplit.{pane, removeSelectedComponents}
import scalafx.scene.control.{Button, Label, Separator, ToggleButton, ToolBar}
import dashboard.UIElements.FunctionalityElements.LeftSplit.hideComponent
import dashboard.lib.Api.getDataFromAlphavantageAndSave
import scalafx.beans.Observable.sfxObservable2jfx
import scalafx.collections.ObservableBuffer
import scalafx.scene.Node

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
    println("The button is working!!")
    getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Apple")
    getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Microsoft")
    getDataFromAlphavantageAndSave("TIME_SERIES_MONTHLY", "Nvidia")

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