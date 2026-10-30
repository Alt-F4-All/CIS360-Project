# NFL Player Statistics and Forecasting

## Project Overview

This project is a Java-based NFL player statistics and forecasting application developed for CIS360.

The program retrieves player statistics from a 2025 NFL weekly statistics CSV file and uses several quantitative forecasting methods to predict player performance.

The project currently supports: quarterbacks (QB), running backs (RB), wide receivers (WR).

## Features

- Retrieve weekly statistics for NFL players.
- Support position-specific statistics for QB, RB, and WR.
- Generate forecasts using seven forecasting methods:
  - Naive forecasting
  - Simple moving average (six games)
  - Simple moving average (all games)
  - Weighted moving average (six games)
  - Weighted moving average (all games)
  - Exponential smoothing
  - Linear trend forecasting
- Compare forecasts against the actual result of the 17th game played.
- Identify the most and least accurate forecasting methods for each statistic.

## Requirements

- Java Development Kit (JDK) 17 or newer
- A terminal or command prompt

All required third-party JAR dependencies are included in the `lib/` directory.

## Project Structure

- `src/` — Java source code
- `data/` — NFL player statistics CSV dataset
- `lib/` — Required third-party Java libraries
- `bin/` — Compiled Java classes (generated locally)

## How to Compile and Run

Open a terminal in the project's root directory.

### macOS / Linux

```bash
mkdir -p bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin:lib/*" Main
```

### Windows PowerShell

```powershell
New-Item -ItemType Directory -Force bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin;lib/*" Main
```

## Data

The application uses the file:

`data/stats_player_week_2025.csv`

The CSV file must remain in this location relative to the project root.

## Forecasting Methodology

For players with 17 recorded games, the program uses the first 16 games to forecast the 17th game.

Each forecasting method produces one prediction per statistic. The prediction is compared against the actual 17th-game result using absolute error.

The method with the lowest absolute error is identified as the most accurate for that player and statistic. The method with the highest absolute error is identified as the least accurate.

## Notes

- Run the application from the project root directory.
- Compiled `.class` files are not included in version control.
- Players must have sufficient historical game data for the forecasting methods being used.
- The 17th game played is not necessarily the same as NFL Week 17.