import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumberGame 
{
    private static final int MAX_ATTEMPTS = 5;

    public static void main(String[] args) 
    {
        ArrayList<String> numberNames = readNumberNamesFromFile("numberNames.txt");
        if (numberNames.isEmpty())
        {
            System.out.println("Failed to load number names from file.");
            return;
        }

        Random random = new Random();
        String randomNumberName = numberNames.get(random.nextInt(numberNames.size()));
        char[] clue = generateClue(randomNumberName);

        System.out.println("Welcome to Guess the Number Game!");
        System.out.println("I've chosen a number name from 0 to 20. You have 5 attempts to guess it.");

        Scanner scanner = new Scanner(System.in);
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) 
        {
            System.out.println("Clue: " + new String(clue));
            System.out.print("Attempt " + (attempts + 1) + ": Enter your guess (letter): ");
            String userGuess = scanner.nextLine().toLowerCase();

            if (userGuess.length() == 1) 
            {
                if (updateClue(randomNumberName, clue, userGuess.charAt(0))) 
                {
                    System.out.println("Correct guess!");
                } 
                else 
                {
                    System.out.println("Incorrect guess. Try again.");
                    attempts++;
                }
            } 
            else 
            {
                System.out.println("Invalid input. Please enter a single letter.");
            }

            if (isComplete(clue)) 
            {
                System.out.println("Congratulations! You guessed it right. The number name was \"" + randomNumberName + "\".");
                return;
            }
        }

        System.out.println("Sorry, you've used all your attempts. The correct number name was \"" + randomNumberName + "\".");
    }

    private static ArrayList<String> readNumberNamesFromFile(String filename) 
    {
        ArrayList<String> numberNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                numberNames.add(line.trim().toLowerCase());
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return numberNames;
    }

    private static char[] generateClue(String numberName) 
    {
        char[] clue = new char[numberName.length()];
        for (int i = 0; i < numberName.length(); i++) 
        {
            clue[i] = '_';
        }
        return clue;
    }

    private static boolean updateClue(String randomNumberName, char[] clue, char guess) 
    {
        boolean correctGuess = false;
        for (int i = 0; i < randomNumberName.length(); i++) 
        {
            if (randomNumberName.charAt(i) == guess) 
            {
                clue[i] = guess;
                correctGuess = true;
            }
        }
        return correctGuess;
    }

    private static boolean isComplete(char[] clue) 
    {
        for (char c : clue) 
        {
            if (c == '_') 
            {
                return false;
            }
        }
        return true;
    }
}