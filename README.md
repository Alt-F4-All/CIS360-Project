# NFL Player Analytics and Forecasting

A CIS360 Systems Analysis and Design project for exploring NFL player statistics and comparing forecasting methods. The project supports quarterbacks (QB), running backs (RB), and wide receivers (WR) using 2025 player data.

## Features

- Retrieve player statistics by position and name.
- Generate predictions with seven forecasting methods in Java.
- Compare predictions with an actual game result using absolute error.
- Display player statistics through a Python Streamlit dashboard connected to Snowflake.
- Use SQL queries to retrieve statistics for selected players.

## Technologies

| Technology | Purpose |
| --- | --- |
| Java | Data preparation and forecasting calculations |
| OpenCSV | Read the weekly CSV dataset |
| Snowflake | Store and query player statistics |
| SQL | Retrieve player data |
| Python and Streamlit | Build the statistics dashboard |
| GitHub | Share code and track project changes |

## Project Structure

| Path | Contents |
| --- | --- |
| `java-backend/src/` | Java application and forecasting methods |
| `java-backend/data/` | Weekly player statistics CSV |
| `java-backend/lib/` | Required Java libraries |
| `java-backend/README.md` | Additional Java documentation |
| `snowflake/` | NFL datasets, SQL examples, and Streamlit dashboard code |

## Forecasting Approach

The Java program uses the first 16 recorded regular-season games to predict the 17th recorded game for a selected player. It compares each prediction with the actual result and reports the methods with the lowest and highest absolute errors for each statistic.

The seven methods are:

1. Naive forecasting
2. Simple moving average — latest six games
3. Simple moving average — all historical games
4. Weighted moving average — latest six games
5. Weighted moving average — all historical games
6. Exponential smoothing — alpha = 0.3
7. Linear trend

This is a historical evaluation of forecasting methods. The current Java workflow requires exactly 17 recorded games, listed in chronological order. A player's 17th game is not necessarily NFL Week 17.

## Run the Java Application

**Requirements:** JDK 17 or newer and a terminal. Required JAR libraries are included in `java-backend/lib/`.

Download or clone this repository, then open a terminal in the repository folder.

### macOS / Linux

```bash
cd java-backend
mkdir -p bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin:lib/*" Main
```

### Windows PowerShell

```powershell
cd java-backend
New-Item -ItemType Directory -Force bin
javac -cp "lib/*" -d bin src/*.java
java -cp "bin;lib/*" Main
```

Enter a supported player's name when prompted. The application prints forecasts, the actual 17th-game result, and the best and worst methods for each statistic.

Keep `data/stats_player_week_2025.csv` in its existing location and run the application from `java-backend/`.

## Snowflake Dashboard

The dashboard source is in [`snowflake/snowflake_streamlitapp`](snowflake/snowflake_streamlitapp). It is Python code designed for a Streamlit environment with an active Snowflake session.

Users select a position and player to view their team, games played, and position-specific statistics. The dashboard currently reads `NFLDATAREG.PUBLIC.NFLTABLE`; that table must contain the expected player data and be accessible to the active session.

Example queries are provided in [`snowflake/queries.sql`](snowflake/queries.sql). They reference `NFLDATA.PUBLIC.TABLE1`, so adjust the table names to match your Snowflake setup. The dashboard relies on its source table containing the intended 2025 data.

## Current Status

The Java forecasting program and Snowflake dashboard are separate components. The dashboard currently displays recorded statistics; it does not display Java forecast results. Connecting these components is a next development step.

The project focuses on individual player statistics. Team win predictions are not currently implemented.
