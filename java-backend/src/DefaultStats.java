//Made by Sawyer Jacobson
//This is basically a pseudo-JSON file to store default statistics for position groups.


import java.util.List;
import java.util.Map;

public class DefaultStats {

    private static final Map<String, List<String>> DEFAULT_STATS =
        Map.of(
            "QB", List.of(
                "passing_yards",
                "passing_tds",
                "passing_interceptions"
            ),
            "RB", List.of(
                "rushing_yards",
                "rushing_tds",
                "carries",
                "receptions",
                "receiving_yards"
            ),
            "WR", List.of(
                "receiving_yards",
                "receptions",
                "targets",
                "receiving_tds"
            )
        );

    public static List<String> getStats(String position) {
        return DEFAULT_STATS.getOrDefault(
            position.toUpperCase(),
            List.of()
        );
    }
}
