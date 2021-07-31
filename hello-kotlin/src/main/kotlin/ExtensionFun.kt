import javafx.application.Application
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

class MyApp : Application() {
    override fun start(primaryStage: Stage) {
        val root = VBox().apply {
            label("Something, something")
            button("Oranges").setOnAction {
                println("Something!")
            }
        }

        with(primaryStage) {
            scene = Scene(root)
            show()
        }
    }
}

fun Pane.label(text: String) = add(Label(text))

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