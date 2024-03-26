package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.FunctionalityElements.RightSplit.{pane, removeSelectedComponents}
import scalafx.scene.control.{Button, Label, Separator, ToggleButton, ToolBar}
import dashboard.UIElements.FunctionalityElements.LeftSplit.hideComponent
import scalafx.beans.Observable.sfxObservable2jfx
import scalafx.collections.ObservableBuffer
import scalafx.scene.Node

object ToolBar:

  val (
    selectButton,
    hideButton,
    removeButton,
    refreshButton,
    lastRefreshed
  ) = (
    ToggleButton("Select"),
    Button("Hide"),
    Button("Remove"),
    Button("Refresh"),
    Label("Last Refreshed: DUNNO??")
  )

  hideButton.onAction = (event) =>
    val removedComponents = removeSelectedComponents()
    removedComponents.foreach(hideComponent)

  removeButton.onAction = (event) =>
    removeSelectedComponents()


  def getToolBarElement: ToolBar =

    val toolBar = new ToolBar:
      items = Array(
        selectButton,
        new Separator,
        hideButton,
        removeButton,
        new Separator,
        refreshButton,
        lastRefreshed
      )

    toolBar