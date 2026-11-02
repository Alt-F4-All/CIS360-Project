#SQLQUERIES 
  

-- QB Passing Stats (Bo Nix vs Matthew Stafford)

SELECT
    PLAYER_DISPLAY_NAME,
    PASSING_YARDS,
    PASSING_TDS,
    PASSING_INTERCEPTIONS
FROM NFLDATA.PUBLIC.TABLE1
WHERE PLAYER_DISPLAY_NAME IN (
    'Bo Nix',
    'Matthew Stafford'
);


-- RB Rushing Stats (Quinshon Judkins vs J.K. Dobbins)

SELECT
    PLAYER_DISPLAY_NAME,
    RUSHING_YARDS,
    RUSHING_TDS,
    CARRIES
FROM NFLDATA.PUBLIC.TABLE1
WHERE PLAYER_DISPLAY_NAME IN (
    'Quinshon Judkins',
    'J.K. Dobbins'
);


-- WR Receiving Stats (Jaylen Waddle vs Courtland Sutton)

SELECT
    PLAYER_DISPLAY_NAME,
    RECEIVING_YARDS,
    RECEPTIONS,
    TARGETS
FROM NFLDATA.PUBLIC.TABLE1
WHERE PLAYER_DISPLAY_NAME IN (
    'Jaylen Waddle',
    'Courtland Sutton'
);
