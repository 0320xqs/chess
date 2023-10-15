package ChessGames.ChineseChess.Model;

import ChessGames.ChineseChess.AI.PieceScore;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.nd4j.shade.jackson.annotation.JsonValue;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public enum ChessRole {
    BLACK_KING(Part.SECOND, ChessImage.BLACK_KING.image, PieceScore.KING),
    BLACK_MINISTER(Part.SECOND, ChessImage.BLACK_MINISTER.image, PieceScore.MINISTER),
    BLACK_ELEPHANT(Part.SECOND, ChessImage.BLACK_ELEPHANT.image, PieceScore.ELEPHANT),
    BLACK_HORSE(Part.SECOND, ChessImage.BLACK_HORSE.image, PieceScore.HORSE),
    BLACK_ROOK(Part.SECOND, ChessImage.BLACK_ROOK.image, PieceScore.ROOK),
    BLACK_CANNON(Part.SECOND, ChessImage.BLACK_CANNON.image, PieceScore.CANNON),
    BLACK_SOLDIER(Part.SECOND, ChessImage.BLACK_SOLDIER.image, PieceScore.SOLDIER),
    RED_KING(Part.FIRST, ChessImage.RED_KING.image, PieceScore.KING),
    RED_MINISTER(Part.FIRST, ChessImage.RED_MINISTER.image, PieceScore.MINISTER),
    RED_ELEPHANT(Part.FIRST, ChessImage.RED_ELEPHANT.image, PieceScore.ELEPHANT),
    RED_HORSE(Part.FIRST, ChessImage.RED_HORSE.image, PieceScore.HORSE),
    RED_ROOK(Part.FIRST, ChessImage.RED_ROOK.image, PieceScore.ROOK),
    RED_CANNON(Part.FIRST, ChessImage.RED_CANNON.image, PieceScore.CANNON),
    RED_SOLDIER(Part.FIRST, ChessImage.RED_SOLDIER.image, PieceScore.SOLDIER);
    Part part;
    Image image;
    PieceScore pieceScore;

    ChessRole(Part part, Image image, PieceScore pieceScore) {
        this.part = part;
        this.image = image;
        this.pieceScore = pieceScore;
    }


    public void setPart(Part part) {
        this.part = part;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPieceScore(PieceScore pieceScore) {
        this.pieceScore = pieceScore;
    }

}
