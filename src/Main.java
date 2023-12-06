import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static final String WRONG_INPUT_FILE_MESSAGE = "Wrong file";
    public static final String INPUT_FILENAME = "input";

    public static void main(String[] args) throws IOException {
        List<RaceData> raceDataList = buildRaceDataListFrom(extractInputLines());

        long result = raceDataList.stream()
                .mapToLong(Main::getAmountOfWinsPossible)
                .reduce(1, (a, b) -> a * b);

        System.out.println("Result is: " + result);
    }

    private static long getAmountOfWinsPossible(RaceData raceData) {
        int amountOfWinsPossible = 0;

        for (long millisecondsHeldAtStart = 0; millisecondsHeldAtStart < raceData.time(); millisecondsHeldAtStart++) {
            long distanceTravelled = millisecondsHeldAtStart * (raceData.time() - millisecondsHeldAtStart);

            if (distanceTravelled > raceData.distance()) {
                amountOfWinsPossible++;
            }
        }

        return amountOfWinsPossible;
    }

    private static List<RaceData> buildRaceDataListFrom(List<String> inputLines) {
        long time = Long.parseLong(inputLines.get(0).split(":")[1].trim().replaceAll("\\s+", ""));
        long distance = Long.parseLong(inputLines.get(1).split(":")[1].trim().replaceAll("\\s+", ""));

        return List.of(new RaceData(time, distance));
    }

    private static List<String> extractInputLines() throws IOException {
        try (InputStream resource = Main.class.getResourceAsStream(INPUT_FILENAME)) {
            if (resource == null) {
                throw new RuntimeException(WRONG_INPUT_FILE_MESSAGE);
            }

            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))
                    .lines()
                    .toList();
        }
    }

    record RaceData(long time, long distance) {}
}
