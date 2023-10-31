
import java.io.*;
import java.util.*;

public class Brainfuck {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        char[] program;
        if (args.length > 0) {
            // Read program from file
            try {
                program = readFile(args[0]);
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return;
            }
        } else {
            // Read program from standard input
            System.out.println("Enter brainfuck program. Press Enter twice to run the program.");

            StringBuilder sb = new StringBuilder();
            String str;
            while (sc.hasNextLine()) {
                str = sc.nextLine();
                if (str.isEmpty()) {
                    break;
                }
                sb.append(str);
            }
            program = sb.toString().toCharArray();
        }

        int[] cells = new int[30000];

        Stack<Integer> startingLoopInstructionPointers = new Stack<>();
        Queue<Integer> inputBuffer = new LinkedList<>();

        int cellPointer = 0;
        int instructionPointer = 0;

        while (instructionPointer < program.length) {

            char instruction = program[instructionPointer];

            switch (instruction) {

                case '+' -> {
                    if (cells[cellPointer] != 255) {
                        cells[cellPointer]++;
                    } else {
                        cells[cellPointer] = 0;
                    }
                    instructionPointer++;
                }

                case '-' -> {
                    if (cells[cellPointer] != 0) {
                        cells[cellPointer]--;
                    } else {
                        cells[cellPointer] = 255;
                    }
                    instructionPointer++;
                }
                case '>' -> {
                    if (cellPointer != cells.length - 1) {
                        cellPointer++;
                    }
                    instructionPointer++;
                }
                case '<' -> {
                    if (cellPointer != 0) {
                        cellPointer--;
                    }
                    instructionPointer++;
                }
                case '.' -> {
                    System.out.print((char) cells[cellPointer]);
                    instructionPointer++;
                }
                case ',' -> {
                    int val;
                    if (inputBuffer.isEmpty()) {
                        String inputLine = sc.nextLine();
                        if (!inputLine.isEmpty()) {
                            char[] input = inputLine.toCharArray();
                            for (char c : input) {
                                inputBuffer.offer((int) c);
                            }
                        }
                    }
                    if (!inputBuffer.isEmpty()) {
                        val = inputBuffer.poll();
                        cells[cellPointer] = val;
                    }
                    instructionPointer++;
                }
                case '[' -> {
                    if (cells[cellPointer] == 0) {
                        int nest = 1;
                        int i = instructionPointer;
                        i++;
                        x:
                        while (i < program.length) {
                            switch (program[i]) {
                                case '[' -> {
                                    nest++;
                                    i++;
                                }
                                case ']' -> {
                                    nest--;
                                    if (nest == 0) {
                                        instructionPointer = i;
                                        instructionPointer++;
                                        break x;
                                    }
                                    i++;
                                }
                                default ->
                                    i++;
                            }
                        }

                    } else {
                        startingLoopInstructionPointers.push(instructionPointer);
                        instructionPointer++;
                    }
                }
                case ']' -> {
                    if (cells[cellPointer] != 0) {
                        instructionPointer = startingLoopInstructionPointers.peek();
                    } else {
                        startingLoopInstructionPointers.pop();
                    }
                    instructionPointer++;
                }
                default ->
                    instructionPointer++;

            }

            //System.out.println(cellPointer + ", " + instructionPointer);
        }

    }

    // Method to read the program from a file
    private static char[] readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString().toCharArray();
    }
}
