package lec_06_prg_03_json_example;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Application {

    public static void main(String[] args) throws FileNotFoundException {
        JsonElement superHeroes = JsonParser.parseReader(new FileReader("./src/lec_06_prg_03_json_example/lec-06-prg-03-json-example.json"));

        System.out.println(superHeroes.getAsJsonObject().get("homeTown").getAsString());
    }
}
