package Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Write {


    public static void write(List<int[]> boards, String chessName) throws IOException {

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int[] board : boards) {
            JsonObject boardObject = new JsonObject();
            JsonArray boardArray = new JsonArray();
            for (int value : board) {
                boardArray.add(value);
            }
            boardObject.add("board", boardArray);
            jsonArray.add(boardObject);
        }
        jsonObject.add("list", jsonArray);

        // create the Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String s = now.format(formatter) + ".json";

        // write the JSON data to a file
        try (FileWriter writer = new FileWriter("example/" + chessName + "/" + s)) {
            gson.toJson(jsonObject, writer);
        }

    }
}
