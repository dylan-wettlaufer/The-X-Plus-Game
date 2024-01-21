In this game two players (a human and a computer) take turns putting tiles on a board which has n rows and n columns. To win the game the player must put at least k tiles on the board to form an X shape of a + shape. 
To compute scores, the program uses a recursive algorithm that repeatedly simulates a play from the computer until an outcome for the game has been decided. This algorithm forms a tree for all possible plays the computer can make.
The algorithm then considers all possible plays from the human player. Then all possible responses by the computer are attempted, and so on until the outcome of each possible sequence of plays is determined.
Every time that the score of a board configuration is computed, the configuration and its score are stored in a dictionary which is implemented using a hash table.

