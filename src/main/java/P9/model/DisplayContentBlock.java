package P9.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class DisplayContentBlock {

    private ObjectProperty<ContentBlock> contentBlockProp = new SimpleObjectProperty<>();
    private ObjectProperty<Button> buttonProp = new SimpleObjectProperty<>();

    public DisplayContentBlock(ContentBlock contentBlock, Button button){
        this.contentBlockProp.set(contentBlock);
        this.buttonProp.set(button);
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
}
