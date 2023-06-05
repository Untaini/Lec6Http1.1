package lec_06_prg_05_json_example;

import com.google.gson.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    static Map<String, Object> superHeroes = Stream.of(new Object[][]{
            {"squadName", "Super hero squad"},
            {"homeTown", "Metro City"},
            {"formed", 2016},
            {"secretBase", "Super tower"},
            {"active", true},
            {"members", Arrays.asList(
                    Stream.of(new Object[][] {
                            {"name", "Molecule Man"},
                            {"age", 29},
                            {"secretIdentity", "Dan Jukes"},
                            {"powers", Arrays.asList(
                                    "Radiation resistance",
                                    "Turning tiny",
                                    "Radiation blast"
                            )}
                    }).collect(Collectors.toMap(entry -> entry[0], entry -> entry[1])),
                    Stream.of(new Object[][] {
                            {"name", "Madame Uppercut"},
                            {"age", 39},
                            {"secretIdentity", "Jane Wilson"},
                            {"powers", Arrays.asList(
                                    "Million tonne punch",
                                    "Damage resistance",
                                    "Superhuman reflexes"
                            )}
                    }).collect(Collectors.toMap(entry -> entry[0], entry -> entry[1])),
                    Stream.of(new Object[][] {
                            {"name", "Eternal Flame"},
                            {"age", 1000000},
                            {"secretIdentity", "Unknown"},
                            {"powers", Arrays.asList(
                                    "Immortality",
                                    "Heat Immunity",
                                    "Inferno",
                                    "Teleportation",
                                    "Interdimensional travel"
                            )}
                    }).collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]))
            )}
    }).collect(Collectors.toMap(entry -> (String) entry[0], entry -> entry[1]));

    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        System.out.println(gson.toJson(superHeroes));
    }
}
