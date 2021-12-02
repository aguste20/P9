package P9.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class DisplayContentBlock {

    private final ObjectProperty<ContentBlock> contentBlockProp = new SimpleObjectProperty<>();
    private final ObjectProperty<Button> buttonProp = new SimpleObjectProperty<>();
    private ObjectProperty<Button> editButtonProp = new SimpleObjectProperty<>();

    public DisplayContentBlock(ContentBlock contentBlock, Button button, Button editBtn){
        this.contentBlockProp.set(contentBlock);
        this.buttonProp.set(button);
        this.editButtonProp.set(editBtn);
    }

    public ContentBlock getContentBlock(){
        return contentBlockProp.get();
    }

    public ObjectProperty<ContentBlock> getContentBlockProp(){
        return contentBlockProp;
    }

    public void setContentBlockProp (ContentBlock contentBlock){
        this.contentBlockProp.set(contentBlock);
    }

    public Button getButton(){
        return buttonProp.get();
    }

    public ObjectProperty<Button> getButtonProp(){
        return buttonProp;
    }

    public void setButtonProp(Button button){
        this.buttonProp.set(button);
    }

    public Button getEditBtn() {
        return editButtonProp.get();
    }

    public ObjectProperty<Button> getEditButtonProp(){
        return editButtonProp;
    }

    public void setEditButtonProp(Button button){
        this.buttonProp.set(button);
    }

}
