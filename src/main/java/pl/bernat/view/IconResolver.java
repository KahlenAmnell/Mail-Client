package pl.bernat.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconResolver {
    public Node getIconForFolder(String folderName){
        String lowerCaseFolderName = folderName.toLowerCase();
        ImageView imageView;
        try {
            if(lowerCaseFolderName.contains("@")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/view/icons/email.png")));
            } else if (lowerCaseFolderName.contains("inbox")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/view/icons/folder.png")));
            } else if (lowerCaseFolderName.contains("sent")){
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/view/icons/sent2.png")));
            } else if (lowerCaseFolderName.contains("spam")) {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/view/icons/spam.png")));
            } else {
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/view/icons/folder.png")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        imageView.setFitWidth(16);
        imageView.setFitHeight(16);
        return imageView;
    }
}
