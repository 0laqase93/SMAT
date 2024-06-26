package celestial.smat.Classes;

import celestial.smat.PrincipalController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Controlador de navegación para la interacción con la escena.
 */
public class NavigationController {
    private final AnchorPane space;
    private final Scene scene;

    private static final double SCALE_DELTA = 1.1;

    /**
     * Constructor del controlador de navegación.
     *
     * @param space El panel de anclaje donde se realiza la navegación.
     * @param scene La escena en la que se realiza la navegación.
     */
    public NavigationController(AnchorPane space, Scene scene) {
        this.space = space;
        this.scene = scene;

        zoom();
        move();
        addGrid();
    }

    /**
     * Establece la funcionalidad de zoom en la escena.
     */
    private void zoom() {
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor;
                if (event.getDeltaY() > 0) {
                    scaleFactor = SCALE_DELTA;
                } else {
                    scaleFactor = 1 / SCALE_DELTA;
                }

                space.setScaleX(space.getScaleX() * scaleFactor);
                space.setScaleY(space.getScaleY() * scaleFactor);
            }
        });
    }

    /**
     * Establece la funcionalidad de movimiento en la escena mientras se mantiene presionado el botón de control.
     */
    private void move() {
        final double[] orgSceneXY = new double[2];

        scene.setOnMousePressed(event -> {
            if (event.isControlDown()) {
                orgSceneXY[0] = event.getSceneX();
                orgSceneXY[1] = event.getSceneY();
            }
        });

        scene.setOnMouseDragged(event -> {
            if (event.isControlDown()) {
                double offsetX = event.getSceneX() - orgSceneXY[0];
                double offsetY = event.getSceneY() - orgSceneXY[1];
                double newTranslateX = space.getTranslateX() + offsetX;
                double newTranslateY = space.getTranslateY() + offsetY;

                space.setTranslateX(newTranslateX);
                space.setTranslateY(newTranslateY);

                orgSceneXY[0] = event.getSceneX();
                orgSceneXY[1] = event.getSceneY();
            }
        });
    }

    public void addGrid() {

        double w = space.getBoundsInLocal().getWidth();
        double h = space.getBoundsInLocal().getHeight();

        Canvas grid = new Canvas(w, h);

        grid.setMouseTransparent(true);

        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);

        double offset = 100;
        for( double i=offset; i < w; i+=offset) {
            gc.strokeLine( i, 0, i, h);
            gc.strokeLine( 0, i, w, i);
        }

        space.getChildren().add( grid);

        grid.toBack();
    }
}
