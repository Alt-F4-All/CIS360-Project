
//Made by Sawyer Jacobson
//This main method takes in a player's name and then communicates with the StatsScraping and Forecasting 
//  classes to output the predictions for each statistic for a player.

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    //This is a helper method that exists so you can put in player names case-insensitively.
    // Example usage:
    // toTitleCase("CHAD JOHNSON") -> "Chad Johnson"
    // toTitleCase("chad Johnson") -> "Chad Johnson"
    public static String toTitleCase(String input) {
    if (input == null || input.isEmpty()) {
        return input;
    }

    String[] words = input.trim().toLowerCase().split("\\s+");
    StringBuilder formattedName = new StringBuilder();

    for (String word : words) {
        if (!word.isEmpty()) {
            // Capitalize the first letter and append the rest of the word
            formattedName.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1))
                         .append(" ");
        }
    }
    
    return formattedName.toString().trim();
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String[]> playerStats = new ArrayList<>();
        boolean anytimeInvalid = false;
        String playerName;
        try (Scanner scanner = new Scanner(System.in)) {
            
            //This while loop collects the player name using the scanner and checks both 
            //wether the name is valid AND if its the right position.
            while(!anytimeInvalid) { 
                System.out.print("Player name: ");
                playerName = toTitleCase(scanner.nextLine().trim());
                playerStats = StatsScraping.playerStatsAllWeeks(playerName);

                try { 
                    playerStats.get(0);
                }

                catch (IndexOutOfBoundsException e) {
                    System.err.println("Player name not found. Please try again.");
                    continue;
                }
                String[] generalStatsReference = playerStats.get(0);
                if (DefaultStats.getStats(generalStatsReference[4]).isEmpty()) {
                    System.err.println("Currently this program only supports QB, WR, and RB players. Please enter a player under this position.");
                    continue;
                }
                anytimeInvalid = true;
            }
        }

        //This code takes the player Statistics and sends it to the Forecasting method to be predicted.
        String[] defaultStats = StatsScraping.getDefaultStatistics(playerStats);
        List<float[]> listOfStatsByWeek = StatsScraping.parseWeeksForForecasting(playerStats);
        for (int i = 0; i < listOfStatsByWeek.size(); i++) {
            Forecasting.allForecasts(listOfStatsByWeek.get(i), defaultStats[i]);
        }
    }
}