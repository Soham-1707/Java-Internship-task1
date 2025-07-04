import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Guessgame {

    private static final int MAX_ATTEMPTS = 7;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 100;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean keepPlaying = true;
        int gamesPlayed = 0;
        int gamesWon = 0;

        System.out.println("=== Welcome to the Number Guessing Game ===");

        while (keepPlaying) {
            gamesPlayed++;
            if (runGame(input)) {
                gamesWon++;
            }

            System.out.print("Would you like to play again? (y/n): ");
            String choice = input.next().trim().toLowerCase();
            keepPlaying = choice.equals("y");
        }

        input.close();
        System.out.println("\nSummary: " + gamesWon + " win(s) out of " + gamesPlayed + " game(s).");
        writeToLog("Session: " + gamesPlayed + " played, " + gamesWon + " won.");
    }

    private static boolean runGame(Scanner scanner) {
        Random generator = new Random();
        int secret = generator.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;

        System.out.println("\nI've picked a number between " + MIN_VALUE + " and " + MAX_VALUE + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " guesses. Good luck!");

        for (int guessNum = 1; guessNum <= MAX_ATTEMPTS; guessNum++) {
            System.out.print("Guess #" + guessNum + ": ");
            int userGuess = scanner.nextInt();

            if (userGuess == secret) {
                System.out.println("That's correct! Well done.");
                writeToLog("Success on try " + guessNum + ". Number was: " + secret);
                return true;
            } else if (userGuess < secret) {
                System.out.println("Too low.");
            } else {
                System.out.println("Too high.");
            }
        }

        System.out.println("No more tries left. The correct number was " + secret + ".");
        writeToLog("Failed to guess. Number was: " + secret);
        return false;
    }

    private static void writeToLog(String content) {
        try (FileWriter writer = new FileWriter("guess_game_log.txt", true)) {
            String timestamp = LocalDateTime.now().toString();
            writer.write("[" + timestamp + "] " + content + "\n");
        } catch (IOException ex) {
            System.out.println("Error: Could not write to log file.");
        }
    }
}
