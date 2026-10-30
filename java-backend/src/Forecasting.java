//Initially written by Sawyer Jacobson, and then overhauled by ChatGPT.

import java.util.Arrays;

/** Predicts one held-out final game for each statistic. */
public class Forecasting {

    @FunctionalInterface
    public interface ForecastMethod {
        float predict(float[] history);
    }

    /**
     * games[0..15] are historical observations; games[16] is the actual
     * result of the 17th game played. No prediction method sees games[16].
     */
    public static void allForecasts(float[] games, String statistic) {
        if (games == null || games.length != 17) {
            throw new IllegalArgumentException(
                    "Expected exactly 17 games played: 16 historical results and 1 actual result.");
        }
        requireGames(games, 17);

        float[] history = Arrays.copyOf(games, 16);
        float actual = games[16];
        String[] names = {
                "Naive", "Simple MA (6 games)", "Simple MA (all games)",
                "Weighted MA (6 games)", "Weighted MA (all games)",
                "Exponential smoothing (alpha = 0.3)", "Linear trend"
        };
        ForecastMethod[] methods = {
                Forecasting::naiveForecast,
                Forecasting::simpleMASixGames,
                Forecasting::simpleMAAllGames,
                Forecasting::weightedMASixGames,
                Forecasting::weightedMAAllGames,
                h -> exponentialSmoothing(h, 0.3f),
                Forecasting::linearTrendForecast
        };

        String bestName = null, worstName = null;
        float bestError = Float.POSITIVE_INFINITY;
        float worstError = Float.NEGATIVE_INFINITY;

        System.out.println("\nNext-game forecasts for " + statistic + ":");
        for (int i = 0; i < methods.length; i++) {
            float predicted = methods[i].predict(history);
            float absoluteError = Math.abs(actual - predicted);
            System.out.println(names[i] + ": " + predicted);

            if (absoluteError < bestError) {
                bestError = absoluteError;
                bestName = names[i];
            }
            if (absoluteError > worstError) {
                worstError = absoluteError;
                worstName = names[i];
            }
        }
        System.out.println("Actual result (17th game played): " + actual);
        System.out.println("Best forecast: " + bestName + " (absolute error: " + bestError + ")");
        System.out.println("Worst forecast: " + worstName + " (absolute error: " + worstError + ")");
    }

    private static void requireGames(float[] games, int minGames) {
        if (games == null || games.length < minGames) {
            throw new IllegalArgumentException("requires at least " + minGames + " game(s)");
        }
        for (float game : games) {
            if (!Float.isFinite(game)) {
                throw new IllegalArgumentException("game statistics must be finite numbers");
            }
        }
    }

    public static float naiveForecast(float[] games) {
        requireGames(games, 1);
        return games[games.length - 1];
    }

    public static float simpleMASixGames(float[] games) {
        requireGames(games, 6);
        float sum = 0;
        for (int i = games.length - 6; i < games.length; i++) {
            sum += games[i];
        }
        return sum / 6;
    }

    public static float simpleMAAllGames(float[] games) {
        requireGames(games, 1);
        float sum = 0;
        for (float game : games) {
            sum += game;
        }
        return sum / games.length;
    }

    public static float weightedMASixGames(float[] games) {
        requireGames(games, 6);
        float[] weightsNewestFirst = {0.30f, 0.25f, 0.20f, 0.12f, 0.08f, 0.05f};
        float prediction = 0;
        for (int i = 0; i < weightsNewestFirst.length; i++) {
            prediction += games[games.length - 1 - i] * weightsNewestFirst[i];
        }
        return prediction;
    }

    // Preserved from your original code; used for the all-games weight denominator.
    public static int weightedMAAllGamesRecursiveHelper(int numOfGames) {
        if (numOfGames < 0) {
            throw new IllegalArgumentException("number of games cannot be negative");
        }
        if (numOfGames == 0) {
            return 0;
        }
        return numOfGames + weightedMAAllGamesRecursiveHelper(numOfGames - 1);
    }

    public static float weightedMAAllGames(float[] games) {
        requireGames(games, 1);
        int denominator = weightedMAAllGamesRecursiveHelper(games.length);
        float prediction = 0;
        for (int i = 0; i < games.length; i++) {
            prediction += games[i] * ((float) (i + 1) / denominator);
        }
        return prediction;
    }

    public static float exponentialSmoothing(float[] games, float alpha) {
        requireGames(games, 1);
        if (!Float.isFinite(alpha) || alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("alpha must be greater than 0 and at most 1");
        }

        float forecast = games[0];
        for (int i = 1; i < games.length; i++) {
            forecast = alpha * games[i] + (1 - alpha) * forecast;
        }
        return forecast;
    }

    public static float linearTrendForecast(float[] games) {
        requireGames(games, 2);
        int n = games.length;
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = i + 1;
            double y = games[i];
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;
        return (float) (intercept + slope * (n + 1));
    }

    /** Error for a single held-out game. */
    public static float absoluteError(float actual, float predicted) {
        return Math.abs(actual - predicted);
    }

    /** Squared error for a single held-out game. */
    public static float squaredError(float actual, float predicted) {
        float error = actual - predicted;
        return error * error;
    }
}
