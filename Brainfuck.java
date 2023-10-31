
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Brainfuck {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter brainfuck program. Press Enter twice to run the program.");

        StringBuilder sb = new StringBuilder();
        String input;
        while (sc.hasNextLine()) {
            input = sc.nextLine();
            if (input.isEmpty()) {
                break;
            }
            sb.append(input);
        }

        char[] chars = sb.toString().toCharArray();

        int[] cells = new int[30000];

        Stack<Integer> loops = new Stack<>();

        int cellPointer = 0;

        int programCounter = 0;

        while (programCounter < chars.length) {

            char c = chars[programCounter];

            switch (c) {

                case '+':
                    if (cells[cellPointer] != 255) {
                        cells[cellPointer]++;
                    } else {
                        cells[cellPointer] = 0;
                    }
                    programCounter++;
                    break;

                case '-':
                    if (cells[cellPointer] != 0) {
                        cells[cellPointer]--;
                    } else {
                        cells[cellPointer] = 255;
                    }
                    programCounter++;
                    break;
                case '>':
                    if (cellPointer != cells.length - 1) {
                        cellPointer++;
                    }
                    programCounter++;
                    break;
                case '<':
                    if (cellPointer != 0) {
                        cellPointer--;
                    }
                    programCounter++;
                    break;
                case '.':
                    System.out.print((char) cells[cellPointer]);
                    programCounter++;
                    break;
                case ',':
                    String str = sc.nextLine();
                    if (!str.isEmpty()) {
                        int val = str.charAt(0);
                        cells[cellPointer] = val;
                    }
                    programCounter++;
                    break;
                case '[':
                    if (cells[cellPointer] == 0) {
                        int nest = 1;
                        int i = programCounter;
                        i++;
                        x:
                        while (i < chars.length) {
                            switch (chars[i]) {
                                case '[':
                                    nest++;
                                    i++;
                                    break;
                                case ']':
                                    nest--;
                                    if (nest == 0) {
                                        programCounter = i;
                                        programCounter++;
                                        break x;
                                    }
                                    i++;
                                    break;
                                default:
                                    i++;
                                    break;
                            }
                        }

                    } else {
                        loops.push(programCounter);
                        programCounter++;
                    }
                    break;
                case ']':
                    if (cells[cellPointer] != 0) {
                        programCounter = loops.peek();
                        programCounter++;
                    } else {
                        loops.pop();
                        programCounter++;
                    }
                    break;
                default:
                    programCounter++;

            }

            //System.out.println(cellPointer + ", " + programCounter);
        }

    }
}
