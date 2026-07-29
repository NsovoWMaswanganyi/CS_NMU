package nmu.wrap301.jfx03;

import javafx.scene.Node;

// If a node can have something dropped onto it, then it should implement this interface.
public interface DropTarget {
    // Will this node accept the node being dropped onto it?
    boolean willAcceptDrop(Node node);

    // Process the node dropped onto it. Assumes that willAcceptDrop will have returned
    // true, i.e. the drop target knows how to process the dropped node.
    void processDrop(Node node);
}
