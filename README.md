# Minesweeper
## Introduction
This was a group project that I made in my Java programming class at Jacksonville State University. The purpose of this program was to make a game that is similar to Microsoft's minesweeper game. This game has included features like a timer, flag counter, and a save option. Each game is unique and ends when a bomb is pressed and is won when all the squares that are not bombs are discovered. 
## Instructions
###How to Compile and Run Program
Once Java is downloaded on your machine you can compile the three Java files using command prompt. Once all of the files have been compiled, all you need to do is run the minesweeper.java file.
###Starting a New Game
- Once you run the minesweeper.java file a new game will instantly appear.
- You can start a new game by pressing on any square. The timer will start once a square is pressed.

![start](https://cloud.githubusercontent.com/assets/18745018/16509324/16e8b398-3f01-11e6-8e3c-f00db20cc37a.png)

- A popup screen will appear when you either win or lose.

![won](https://cloud.githubusercontent.com/assets/18745018/16509361/7de6b392-3f01-11e6-89e7-98302a40665b.png)
![gameover](https://cloud.githubusercontent.com/assets/18745018/16509336/363c1ba4-3f01-11e6-91b5-2729f94d169c.png)

- You can save your game by clicking "Save Game" under the options menu.
- Once your game is saved, you can load it back up at anytime by clicking "Load Game" under the options menu.

![savegame](https://cloud.githubusercontent.com/assets/18745018/16509330/2c29e330-3f01-11e6-9a0d-63e4b6bb8b24.png)
![loadgame](https://cloud.githubusercontent.com/assets/18745018/16509340/442de59e-3f01-11e6-98d2-4ede4e667c1c.png)

## Notes 
We used three different classes. The SingleButton class represents a single button on a multidimensional array. The second class, BoardUI, does everything that involves running the game. The minesweeper class is our main class that starts a new game, saves games, loads games. 

## Reflections 
We had issues with making the program open up the surrounding zero spaces when a zero space was pressed. I was able to figure this out by using recursion. A current problem with the prograsm is that the timer does not work when a game is saved. In future versions, I think it would be nice to be able to change the size of the grid.

## Contributors
Eric Whatley, Wayne Myhre, Aaron Rudolph, and Krystyn Gatewood helped make and design this project.
