package ChessGames.GoBang.Model;

import ChessGames.template.Model.Part;
import lombok.Data;
import java.awt.*;

public enum ChessRole {
    BLACKCHESS(Part.SECOND, ChessImage.BLACKCHESS.image),
    WHITECHESS(Part.FIRST, ChessImage.WHITECHESS.image);

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
