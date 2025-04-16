package unsw.loopmania.entity;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

/**
 * A backend entity in the world.
 * 
 * IMPORTANT: The x and y coords refer to the corresponding ImageView node for this
 * entity on the GridPane itis currently in (e.g. the unequipped invetory gridpane,
 * or the equipped inventory gridpane). So when we drag an ImageView for this entity
 * from one gridpane to another, the x and y coords now refer to the NEW gridpane
 * that it was dropped on.
 * 
 * The x and y coords are IntegerProperties, so they have change listeners attatched to
 * them so that when they change, a handle function makes it so that its corresponding
 * ImageView on the frontend also has its x and y coordinates changed in the game view.
 * Hence, the ImageView is essentially updated so that and can change its grid cell
 * within the GridPane it is ALREADY on. The change listeners are attatched in the
 * trackPosition() method in LoopManiaController.
 *
 */
public abstract class Entity {
    /**
     * shouldExist field holds whether the entity should exist as a node within the GUI.
     * using a BooleanProperty here allows us to attach JavaFX ChangeListeners, so that when this BooleanProperty switches to False,
     * a teardown function for the corresponding Node is triggered.
     * This is an example of the Observer pattern!
     * 
     * Note for some entitites this field is redundant e.g. for character, since character is never removed
     * However we have control externally over which ChangeListeners are added (should be done from Controller)
     * 
     * A nice feature of ChangeListeners attached in another class is that the variable scope of the ChangeListener matches the method it was declared in.
     * For example, when setting the ChangeListener for shouldExist for enemies from within the controller, we are able to apply a teardown function which can remove the corresponding JavaFX node from its GridPane
     * which is within the scope of the method defining the ChangeListener.
     * This is why we don't have to track which JavaFX node corresponds to which backend entity explicitly! (for example, in a Hashmap)
     */
    private BooleanProperty shouldExist;

	private Image entityImage;

	/**
     * Create an Entity
     * this constructor should be called for subclass Entities
     */
    public Entity() {
        shouldExist = new SimpleBooleanProperty(true);
    }

     /**
      * Specify that this entity should destroy itself
      * this method will trigger any ChangeListeners attached to shouldExist
      */
    public void destroy() {
        shouldExist.set(false);
    }

    /**
     * return the internally stored BooleanProperty
     * this method is used in the starter code so the LoopManiaWorldController and LoopManiaWorldControllerLoader can attach ChangeListeners
     */
    public BooleanProperty shouldExist() {
        return shouldExist;
    }

    // x, y coordinates will be stored as some form of IntegerProperty so that change listeners can be added
    // which react when the coordinates are updated
    // this is used in the starter code to ensure the coordinates of a JavaFX node are always updated to match its backend Entity

    /**
     * obtain the IntegerProperty representing the x coordinate.
     * We can attach a ChangeListener to this so the x coordinates of the JavaFX node paired with this entity always match.
     * @return IntegerProperty representing x coordinate
     */
    public abstract IntegerProperty x();

    /**
     * obtain the IntegerProperty representing the y coordinate.
     * We can attach a ChangeListener to this so the y coordinate of the JavaFX node paired with this entity always match.
     * @return IntegerProperty representing y coordinate
     */
    public abstract IntegerProperty y();

    /**
     * obtain the current s coordinate as an int.
     * @return s coordinate, as number from 0 to width-1
     */
    public abstract int getX();

    /**
     * obtain the current y coordinate as an int.
     * @return y coordinate, as number from 0 to height-1
     */
    public abstract int getY();

	public void setEntityImageByPath(String path) {
		Image img = new Image((new File(path)).toURI().toString());
		setEntityImage(img);
	}

	public Image getEntityImage() {
		return entityImage;
	}

	public void setEntityImage(Image entityImage) {
		this.entityImage = entityImage;
	}
}
