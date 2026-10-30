//Made by Sawyer Jacobson
//The StatsScraping file does all the work of scraping data from the csv and organizing that player's data
//  to be forecasted in the Forecasting method.


import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class StatsScraping {
    public static final String PATH_TO_DATA_STRING = "data/stats_player_week_2025.csv";
    private static String[] headers;


    //This method takes the entire CSV and turns it into a list of String arrays. Makes it much easier to parse for later methods.
    public static List<String[]> createFullPlayerList() throws FileNotFoundException{
        List<String[]> fullPlayerCSV = new ArrayList<>();
        File playerDataFull = new File(PATH_TO_DATA_STRING);
        String[] nextLine;
        FileReader reader = new FileReader(playerDataFull);
        try (CSVReader csvReader = new CSVReaderBuilder(reader).build()) {
            while ((nextLine = csvReader.readNext()) != null) {
                fullPlayerCSV.add(nextLine);
            }   
        }
        catch (Exception e) {
        }
        headers = fullPlayerCSV.get(0);
        return fullPlayerCSV;
    }
    
    //This method takes a statistic name, and finds its column index so it can be found in any week for any player.
    public static int findHeaderIndex(String statName) {
        int i = 0;
        for (String stat:headers) {
            if (stat.equals(statName)) {
                break;
            }
            i++;
        }
        if (i >= headers.length) {
            throw new IndexOutOfBoundsException("\n\nIndex \"" + statName + "\" could not be found. Check spelling.\n");
        }
        return i;
    }

    //This method takes in a player's name, and compiles their statistics for every week they played in 2025
    //  into a List of String arrays. 
    public static List<String[]> playerStatsAllWeeks(String playerName) throws FileNotFoundException { 
        List<String[]> FPCSV = createFullPlayerList();
        List<String[]> playerStatsAllWeeks = new ArrayList<>();
        int playerNamePosition = findHeaderIndex("player_display_name");
        for (String[] array : FPCSV) {
            if(array[playerNamePosition].equals(playerName)) {
                if(array[8].equals("POST"))
                {}
                else {
                playerStatsAllWeeks.add(array);
            }
        }
    }
    return playerStatsAllWeeks;
}
    //This method reads from the DefaultStats file (basically a json in disguise) and for a player's position group, returns
    //  that position's default statistics.
    public static String[] getDefaultStatistics(List<String[]> playerStats) {
        String[] generalStatsReference = playerStats.get(0);
        String playerPosition = generalStatsReference[4];
        String[] defaultStats = DefaultStats.getStats(playerPosition).toArray(new String[0]);
        return defaultStats;
    }

    //This method takes the List of String arrays from playerStatsAllWeeks and compiles each position group statistic into individual
    //  arrays to format each statistic to be forecasted. It essentially inverts the row-column structure of the CSV by making each row
    //  an individal statistic, and each column a week. 
    public static List<float[]> parseWeeksForForecasting(List<String[]> playerStats) {
        List<float[]> listForForecasting = new ArrayList<>();
        float[] tempArray = new float[playerStats.size()];
        String[] playerStatsSought = getDefaultStatistics(playerStats);
        int i = -1;
            for (String statistic : playerStatsSought) {
                int statPos = findHeaderIndex(statistic);
                for (String[] weekStats : playerStats) {
                    i++;
                    tempArray[i] = Float.parseFloat(weekStats[statPos]);
                }
                listForForecasting.add(tempArray);
                i = -1;
                tempArray = new float[playerStats.size()];
            }
            i = 0;
        return listForForecasting;
    }
    public static void main(String[] args) throws FileNotFoundException {
    }
}
