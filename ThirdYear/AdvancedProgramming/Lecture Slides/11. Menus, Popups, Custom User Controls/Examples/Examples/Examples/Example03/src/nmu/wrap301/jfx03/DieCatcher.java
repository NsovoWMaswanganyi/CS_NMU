package nmu.wrap301.jfx03;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Rectangle;

public class DieCatcher extends Rectangle implements DropTarget {
    // Reference to the die that was caught by this catcher.
    private Die caughtDie = null;

    @Override
    public boolean willAcceptDrop(Node node) {
        // Already caught a die, so cannot catch anything else.
        if(caughtDie != null) return false;

        // Have space. Trying to drop a die here??
        return node instanceof Die;
    }

    @Override
    public void processDrop(Node node) {
        // Catches a die. Can only catch one die at a time. The current implementation
        // shown cannot deal with a caught die being dragged to another catcher and being
        // caught by it. THIS catcher is not notified of the fact, i.e. that it no longer
        // has the die - so it cannot catch new die. How to include this functionality??
        caughtDie = (Die) node;

        // The die catcher centers the dropped die on it. Could do other things
        // as well. NB, with width and the height of the die INCLUDES the special
        // effects, such as the drop shadow. So switching it off before asking
        // for the bounds of the die.
        Effect dieEffect = node.getEffect();
        node.setEffect(null);

        // Get bounds of this node and the dropped die.
        Bounds bounds = getBoundsInParent();
        Bounds dieBounds = node.getBoundsInParent();

        // Switch special effects back on again.
        node.setEffect(dieEffect);

        // Calculate the offset between the CENTER of the die and this node.
        double dX = (bounds.getMinX() + bounds.getMaxX())/2 - (dieBounds.getMinX() + dieBounds.getMaxX())/2;
        double dY = (bounds.getMinY() + bounds.getMaxY())/2 - (dieBounds.getMinY() + dieBounds.getMaxY())/2;

        // Offset the die.
        node.setLayoutX(node.getLayoutX() + dX);
        node.setLayoutY(node.getLayoutY() + dY);
    }
}
