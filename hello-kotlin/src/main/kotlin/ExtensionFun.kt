import javafx.application.Application
import javafx.event.EventTarget
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.util.*

class MyApp : Application() {
    override fun start(primaryStage: Stage) {
        with(primaryStage) {
            vbox(20) {
                paddingAll = 12
                label("Something, something") {
                    style = "-fx-text-fill: green"
                }
                button("Oranges").setOnAction {
                    println("Something!")
                }
            }
            show()
        }
    }
}

fun EventTarget.vbox(spacing: Number?, fn: VBox.() -> Unit) {
    val vbox = VBox()
    if (spacing != null)
        vbox.spacing = spacing.toDouble()

    when(this) {
        is Stage -> scene = Scene(vbox)
        is Pane -> add(vbox)
    }
    fn(vbox)
}

var VBox.paddingAll : Int
    get() = ((padding.left + padding.right + padding.bottom + padding.top) / 4).toInt()
    set(value) {
        padding = Insets(value.toDouble())
    }

fun Pane.label(text: String, fn: (Label.() -> Unit)? = null): Label {
    val label = Label(text)
    add(label)
    fn?.invoke(label)
    return label
}

fun Pane.button(text: String) = Button(text).apply {
    this@button.add(this)
}

fun Pane.add(node: Node) : Pane {
    children.add(node)
    return this
}

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java)
}