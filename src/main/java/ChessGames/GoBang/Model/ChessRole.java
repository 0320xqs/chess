package ChessGames.GoBang.Model;

import ChessGames.template.Model.Part;
import lombok.Data;
import java.awt.*;

public enum ChessRole {
    WHITECHESS(Part.FIRST, ChessImage.WHITECHESS.image),
    BLACKCHESS(Part.SECOND, ChessImage.BLACKCHESS.image);

    Part part;
    Image image;


    ChessRole(Part part, Image image) {
        this.part = part;
        this.image = image;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
