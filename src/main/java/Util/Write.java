package Util;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public static List<int[]> FindRecord(Path path) throws IOException {
        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));

        // 读取整个文件内容
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        reader.close();

        // 解析JSON字符串
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(sb.toString()).getAsJsonObject();
        JsonArray array = json.getAsJsonArray("list");

        // 将JSON数组中的整数数组转换为Java的List<int[]>对象
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            JsonArray board = obj.getAsJsonArray("board");
            int[] intArray = new int[board.size()];
            for (int j = 0; j < board.size(); j++) {
                intArray[j] = board.get(j).getAsInt();
            }
            result.add(intArray);
        }
        return result;
    }
}
