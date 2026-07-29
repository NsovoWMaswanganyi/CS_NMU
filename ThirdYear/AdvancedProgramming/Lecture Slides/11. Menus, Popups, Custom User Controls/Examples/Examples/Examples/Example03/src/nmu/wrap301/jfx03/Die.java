package nmu.wrap301.jfx03;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class Die extends ImageView {
    // region Fields
    // Images shared amongst all instances of dice.
    static private Image[] images = null;

    // Face value displayed on the die
    private IntegerProperty value = new SimpleIntegerProperty(0);

    // Position used for dragging die around.
    private Double anchorX, anchorY;

    // Used to remember if being dragged or not.
    private boolean dragging = false;
    // Used to remember if being rolled or not.
    private boolean rolling = false;

    // Used to remember the cursor used before entering the die.
    private Cursor oldCursor;

    // Animation used to spin the die.
    private RotateTransition rotateAnimation;
    // Animation used to change the image on the die and finally pick a new value.
    private Timeline animation;
    // Animation used during "rolling" a die value.
    private RotateTransition rotationAnimation = null;

    // Effects to display on the control.
    private DropShadow shadow;      // normal shadow for die
    private DropShadow longShadow;  // shadow for when dragging
    private GaussianBlur blur;      // blur for when spinning
    // endregion

    public IntegerProperty valueProperty() {
        return value;
    }

    public Die() {
        super();

        anchorX = Double.valueOf(0);
        anchorY = Double.valueOf(0);

        // region Visual appearance and animations setup.
        // Load images that are shared amongst all instance of Die.
        loadImages();

        // Add drop shadow effect to die
        createEffects();

        // Set the initial effect to the drop shadow
        setEffect(shadow);

        // Create the animations that will be played when rolling the die.
        createAnimations();
        // endregion

        // region Attach behaviour for dragging, double-clicking and value changes.
        // Attach listener to value so that image can be adjusted.
        attachImageUpdateBehaviour();

        // Make the die draggable.
        attachDraggingBehaviour();

        // If double-left click die, then roll it.
        attachClickingBehaviour();
        // endregion

        // Set initial value (and update image).
        roll();
    }

    private void createEffects() {
        // Create regular drop shadow for when die is placed.
        shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setColor(new Color(0, 0, 0, 0.5));

        // Create long drop shadow for when dragging a die.
        longShadow = new DropShadow();
        longShadow.setRadius(10);
        longShadow.setOffsetX(10);
        longShadow.setOffsetY(10);
        longShadow.setColor(new Color(0, 0, 0, 0.5));

        // Create blur to be used while playing the roll animation.
        blur = new GaussianBlur();
        blur.setRadius(4);
    }

    private void loadImages() {
        // Have the images been loaded already (by another die)?
        if (images != null) return;

        // The images have not been loaded yet, so load them.
        images = new Image[6];

        for (int i = 1; i <= 6; i++) {
            Image image = new Image(Die.class.getResourceAsStream("/images/" + i + ".png"));
            images[i - 1] = image;
        }
    }

    private void attachImageUpdateBehaviour() {
        value.addListener((observable, oldValue, newValue) -> {
            if ((newValue.intValue() >= 1) && (newValue.intValue() <= 6)) {
                setImage(images[newValue.intValue() - 1]);
            } else {
                // invalid value, so set to 1 rather
                value.setValue(1);
            }
        });
    }

    private void attachClickingBehaviour() {
        setOnMouseClicked(event -> {
            if ((event.getButton() == MouseButton.PRIMARY) &&
                    (event.getClickCount() == 2)) {
                roll();
            }

            // Prevent the mouse click event being sent to the control(s)
            // underneath this one.
            event.consume();
        });

        setOnMouseEntered(event -> {
            oldCursor = getCursor();
            setCursor(Cursor.OPEN_HAND);
        });

        setOnMouseExited(event -> {
            setCursor(oldCursor);
        });
    }

    private Node lastNode = null;

    private double originalX, originalY;

    private void attachDraggingBehaviour() {
        // Add event handling to allow the die to be dragged around.
        setOnMousePressed(event -> {
            setCursor(Cursor.CLOSED_HAND);

            if (rolling) return;
            if (event.getButton() != MouseButton.PRIMARY) return;

            // Remember offset/displacement/anchor relative to top left corner
            anchorX = event.getScreenX() - getX();
            anchorY = event.getScreenY() - getY();

            // Remember the starting position (if want die to return to it if not accepted
            // for a drop).
            originalX = getX();
            originalY = getY();

            // Bring this die to the top, i.e. won't be drawn underneath other die while moving.
            toFront();
        });

        setOnMouseDragged(event -> {
            if (rolling) return;
            if (event.getButton() != MouseButton.PRIMARY) return;

            // Set top left corner of die so looks like moving at cursor.
            setX(event.getScreenX() - anchorX);
            setY(event.getScreenY() - anchorY);

            // If first time started dragging, change look of die
            if (!dragging) {
                // Make it look like die further away from screen, by making
                // the shadow further away, and...
                setEffect(longShadow);
                // making it 110% of its usual size.
                setScaleX(1.1);
                setScaleY(1.1);
            }

            dragging = true;
        });

        setOnMouseReleased(event -> {
            setCursor(Cursor.OPEN_HAND);

            if (!dragging) return;
            if (rolling) return;
            if (event.getButton() != MouseButton.PRIMARY) return;

            // Make it look closer to screen again, by making
            // the shadow closer, and...
            setEffect(shadow);
            // restoring its size to 100% of the usual size.
            setScaleX(1);
            setScaleY(1);

            // Remember no longer being dragged.
            dragging = false;

            // What happens if dropped onto something that will accept it?
            dropDieOntoTarget();
        });
    }

    private void dropDieOntoTarget() {
        // region If dropped over another node, do something. The "do something"
        // is determined by the node dropped on too - not the die.
        Bounds bounds = getBoundsInParent();
        boolean accepted = false;

        // For each child node that the parent of this node has...
        for (Node node : getParent().getChildrenUnmodifiable()) {
            // Is the child node THIS node? If so, don't do anything (i.e. cannot
            // drop onto itself).
            if (node == this) continue;

            // Does the child node implement the DropTarget interface? If not, don't proceed.
            if (!(node instanceof DropTarget)) continue;

            // Will the child node accept THIS node (the die) being dropped onto it?
            DropTarget dropTarget = (DropTarget) node;
            if (!dropTarget.willAcceptDrop(this)) continue;

            // Finally, do the bounds overlap? If so, drop onto target.
            Bounds nodeBounds = node.getBoundsInParent();
            if (bounds.intersects(nodeBounds)) {
                accepted = true;
                dropTarget.processDrop(this);
                break;
            }
        }

        // If not dropped onto any catcher, return to original position.
        if (!accepted) {
            // Instantly return to original position.
            setX(originalX);
            setY(originalY);

            // region Animate returning to original position.
            /*
            TranslateTransition anim = new TranslateTransition();
            anim.setDuration(new Duration(200));
            anim.setByX(originalX - getX());
            anim.setByY(originalY - getY());
            anim.setInterpolator(Interpolator.EASE_BOTH);
            anim.setNode(this);
            anim.playFromStart();
             */
            // endregion
        }
        // endregion
    }


    public void roll() {
        // Start the animations...
        rotationAnimation.playFromStart(); // start spinning...
        animation.playFromStart(); // start animation that runs code at specific times...
    }

    private void createAnimations() {
        // region Set up a roll animation...
        rotationAnimation = new RotateTransition();
        rotationAnimation.setDuration(new Duration(1000)); // 1 second
        rotationAnimation.setNode(this); // animate the Die itself
        rotationAnimation.setFromAngle(0); // starting angle
        rotationAnimation.setToAngle(360 * 2); // ending angle, i.e. rotate twice
        rotationAnimation.setAutoReverse(false); // don't want to reverse the animation
        rotationAnimation.setCycleCount(1); // only want to do the animation once
        rotationAnimation.setInterpolator(Interpolator.EASE_BOTH); // slowly start spinning, get faster, then slow down again
        // endregion

        // region Set up animation that will execute code at specific times...
        // Create a new timeline that can run the animation.
        animation = new Timeline();

        // At t=0, hide the shadow and blur the die.
        animation.getKeyFrames().add(new KeyFrame(
                new Duration(0),
                event -> {
                    // At time zero, switch off the drop shadow add the blur instead
                    setEffect(blur);
                    rolling = true;
                }));

        // At t=0, 100, 200, ..., 700, change the image displayed to a random one.
        for (int i = 0; i < 8; i++) {
            KeyFrame keyFrame = new KeyFrame(
                    new Duration(i * 100),
                    event -> {
                        Random random = new Random();
                        // setImage(images[random.nextInt(6)]);
                        value.set(random.nextInt(6) + 1);
                    });
            animation.getKeyFrames().add(keyFrame);
        }

        // At t = 1000, stop blurring the die and display the shadow again.
        animation.getKeyFrames().add(new KeyFrame(
                new Duration(1000),
                event -> {
                    // At time 1s, switch on the drop shadow remove the blur
                    setEffect(shadow);
                }));

        // Listen for when the animation is done,
        animation.setOnFinished(event -> {
            // Rolls a new value for the die
            Random random = new Random();
            value.set(random.nextInt(6) + 1);
            // Finished rolling.
            rolling = false;
        });

        // endregion
    }
}
