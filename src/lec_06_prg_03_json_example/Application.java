package lec_06_prg_03_json_example;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Application {
    static final String superHeroesSource = "{" +
            "  \"squadName\": \"Super hero squad\"," +
            "  \"homeTown\": \"Metro City\"," +
            "  \"formed\": 2016," +
            "  \"secretBase\": \"Super tower\"," +
            "  \"active\": True," +
            "  \"members\": [" +
            "    {" +
            "      \"name\": \"Molecule Man\"," +
            "      \"age\": 29," +
            "      \"secretIdentity\": \"Dan Jukes\"," +
            "      \"powers\": [" +
            "        \"Radiation resistance\"," +
            "        \"Turning tiny\"," +
            "        \"Radiation blast\"" +
            "      ]" +
            "    }," +
            "    {" +
            "      \"name\": \"Madame Uppercut\"," +
            "      \"age\": 39," +
            "      \"secretIdentity\": \"Jane Wilson\"," +
            "      \"powers\": [" +
            "        \"Million tonne punch\"," +
            "        \"Damage resistance\"," +
            "        \"Superhuman reflexes\"" +
            "      ]" +
            "    }," +
            "    {" +
            "      \"name\": \"Eternal Flame\"," +
            "      \"age\": 1000000," +
            "      \"secretIdentity\": \"Unknown\"," +
            "      \"powers\": [" +
            "        \"Immortality\"," +
            "        \"Heat Immunity\"," +
            "        \"Inferno\"," +
            "        \"Teleportation\"," +
            "        \"Interdimensional travel\"" +
            "      ]" +
            "    }" +
            "  ]" +
            "}";

    public static void main(String[] args) {
        JsonElement superHeroes = JsonParser.parseString(superHeroesSource);

        System.out.println(superHeroes.getAsJsonObject().get("homeTown").getAsString());
    }
}
