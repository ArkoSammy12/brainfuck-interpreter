import java.util.*;

public class Brainfuck {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

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

        char[] program = sb.toString().toCharArray();

        int[] cells = new int[30000];

        Stack<Integer> startingLoopInstructionPointers = new Stack<>();

        Queue<Integer> inputBuffer = new LinkedList<>();

        int cellPointer = 0;

        int instructionPointer = 0;

        while (instructionPointer < program.length) {

            char instruction = program[instructionPointer];

            switch (instruction) {

                case '+':
                    if (cells[cellPointer] != 255) {
                        cells[cellPointer]++;
                    } else {
                        cells[cellPointer] = 0;
                    }
                    instructionPointer++;
                    break;

                case '-':
                    if (cells[cellPointer] != 0) {
                        cells[cellPointer]--;
                    } else {
                        cells[cellPointer] = 255;
                    }
                    instructionPointer++;
                    break;
                case '>':
                    if (cellPointer != cells.length - 1) {
                        cellPointer++;
                    }
                    instructionPointer++;
                    break;
                case '<':
                    if (cellPointer != 0) {
                        cellPointer--;
                    }
                    instructionPointer++;
                    break;
                case '.':
                    System.out.print((char) cells[cellPointer]);
                    instructionPointer++;
                    break;
                case ',':
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
                    break;
                case '[':
                    if (cells[cellPointer] == 0) {
                        int nest = 1;
                        int i = instructionPointer;
                        i++;
                        x:
                        while (i < program.length) {
                            switch (program[i]) {
                                case '[':
                                    nest++;
                                    i++;
                                    break;
                                case ']':
                                    nest--;
                                    if (nest == 0) {
                                        instructionPointer = i;
                                        instructionPointer++;
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
                        startingLoopInstructionPointers.push(instructionPointer);
                        instructionPointer++;
                    }
                    break;
                case ']':
                    if (cells[cellPointer] != 0) {
                        instructionPointer = startingLoopInstructionPointers.peek();
                    } else {
                        startingLoopInstructionPointers.pop();
                    }
                    instructionPointer++;
                    break;
                default:
                    instructionPointer++;

            }

            //System.out.println(cellPointer + ", " + instructionPointer);
        }

    }
}
