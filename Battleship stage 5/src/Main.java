package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static class Board {
        PlayerOne player;
        //String[] boat;

        public Board() {
            this.player = new PlayerOne();
        }

        String[][] hiddenBoard = {
                {" ","1","2","3","4","5","6","7","8","9","10"},
                {"A","~","~","~","~","~","~","~","~","~","~"},
                {"B","~","~","~","~","~","~","~","~","~","~"},
                {"C","~","~","~","~","~","~","~","~","~","~"},
                {"D","~","~","~","~","~","~","~","~","~","~"},
                {"E","~","~","~","~","~","~","~","~","~","~"},
                {"F","~","~","~","~","~","~","~","~","~","~"},
                {"G","~","~","~","~","~","~","~","~","~","~"},
                {"H","~","~","~","~","~","~","~","~","~","~"},
                {"I","~","~","~","~","~","~","~","~","~","~"},
                {"J","~","~","~","~","~","~","~","~","~","~"}
        };

        String[][] board = {
                {" ","1","2","3","4","5","6","7","8","9","10"},
                {"A","~","~","~","~","~","~","~","~","~","~"},
                {"B","~","~","~","~","~","~","~","~","~","~"},
                {"C","~","~","~","~","~","~","~","~","~","~"},
                {"D","~","~","~","~","~","~","~","~","~","~"},
                {"E","~","~","~","~","~","~","~","~","~","~"},
                {"F","~","~","~","~","~","~","~","~","~","~"},
                {"G","~","~","~","~","~","~","~","~","~","~"},
                {"H","~","~","~","~","~","~","~","~","~","~"},
                {"I","~","~","~","~","~","~","~","~","~","~"},
                {"J","~","~","~","~","~","~","~","~","~","~"}
        };

        public boolean checkShipsLeft() {
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    if (board[i][j].equals("O")) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean entireShipSank(int x, int y) {
            boolean entireShip = false;
            try {
                for (int i = x - 1; i <= x + 1; i++) {
                    try {
                        for (int j = y - 1; j <= y + 1; j++) {
                            if (board[i][j].equals("O")) {
                                return false;
                            }
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {}
            return true;
        }

        public boolean checkIfCorrect(char letter, int number) {
            char[] allowedLetters = {'A','B','C','D','E','F','G','H','I','J'};

            boolean contains = false;
            for (char c : allowedLetters) {
                if (c == letter) {
                    contains = true;
                    break;
                }
            }

            if (number < 1 || number > 10 || !contains) {
                return false;
            }
            return true;
        }

        public boolean shooting(char letter, int number) {
            int x = letter - 64;
            int y = number;
            if (board[x][y].equals("O") || board[x][y].equals("X")) {
                board[x][y] = "X";
                hiddenBoard[x][y] = "X";
                if (entireShipSank(x, y)) {
                    System.out.println("You sank a ship! Specify a new target:");
                } else {
                    System.out.println("You hit a ship! Try again:");
                }
            } else {
                board[x][y] = "M";
                hiddenBoard[x][y] = "M";
                printHiddenBoard();
                System.out.println("You missed!");
            }
            return true;
        }

        public void printHiddenBoard() {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    System.out.print(hiddenBoard[i][j] + " ");
                }
                System.out.println("");
            }
        }

        public void printBoard() {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println("");
            }
        }
        public void addBoat(String[] boat) {
            if (boat[0] != null) {
                int smaller;
                int bigger;
                int smallerLetter;
                int biggerLetter;
                int a = boat[0].charAt(0);
                int b = boat[2].charAt(0);
                if (Integer.valueOf(boat[1]) < Integer.valueOf(boat[3])) {
                    smaller = Integer.valueOf(boat[1]);
                    bigger = Integer.valueOf(boat[3]);
                } else {
                    smaller = Integer.valueOf(boat[3]);
                    bigger = Integer.valueOf(boat[1]);
                }
                if (a < b) {
                    smallerLetter = a;
                    biggerLetter = b;
                } else {
                    smallerLetter = b;
                    biggerLetter = a;
                }

                for (int i = 0; i < (bigger-smaller)+(biggerLetter-smallerLetter)+1; i++) {
                    if (player.isHorizontal(boat)) {
                        board[Math.abs(65 - smallerLetter) + 1][smaller + i] = "O";
                    } else {
                        board[Math.abs(65 - smallerLetter) + 1 + i][smaller] = "O";
                    }
                }
            }
        }
    }

    static class PlayerOne {
        // {"J", "7", "J", "10"}
        String[] carrier = new String[4]; // 5 slots
        String[] battleship = new String[4]; // 4 slots
        String[] submarine = new String[4]; // 3 slots
        String[] cruiser = new String[4]; // 3 slots
        String[] destroyer = new String[4]; // 2 slots

        public boolean isHorizontal(String[] slots) {
            if (Integer.valueOf(slots[1]) != Integer.valueOf(slots[3])) {
                return true;
            } else {
                return false;
            }
        }

        public boolean checkIfClose(int size, String[] boat, Board asd) {
            int smaller;
            int bigger;
            int smallerLetter;
            int biggerLetter;
            int a = boat[0].charAt(0);
            int b = boat[2].charAt(0);
            if (Integer.valueOf(boat[1]) < Integer.valueOf(boat[3])) {
                smaller = Integer.valueOf(boat[1]);
                bigger = Integer.valueOf(boat[3]);
            } else {
                smaller = Integer.valueOf(boat[3]);
                bigger = Integer.valueOf(boat[1]);
            }
            if (a < b) {
                smallerLetter = a;
                biggerLetter = b;
            } else {
                smallerLetter = b;
                biggerLetter = a;
            }
            PlayerOne player = asd.player;
            String[][] board = asd.board;

            ArrayList<Integer[]> kohad = new ArrayList<>();
            Integer[] temp;

            for (int i = 0; i < (bigger-smaller)+(biggerLetter-smallerLetter)+1; i++) {
                if (player.isHorizontal(boat)) {
                    temp = new Integer[]{Math.abs(65 - smallerLetter) + 1, smaller + i};
                    kohad.add(temp);
                } else {
                    temp = new Integer[]{Math.abs(65 - smallerLetter) + 1 + i, smaller};
                    kohad.add(temp);
                }
            }

            for (Integer[] list : kohad) {
                try {
                    for (int i = list[0] - 1; i <= list[0] + 1; i++) {
                        try {
                            for (int j = list[1] - 1; j <= list[1] + 1; j++) {
                                if (board[i][j].equals("O")) {
                                    return false;
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {

                }
            }

            return true;
        }

        public boolean checkShipFits(int size, String[] slots, Board board) {
            // String[] slots     {"F", "7", "F", "10"}
            // Check if ship is not diagonal
            if (!slots[0].equals(slots[2]) && slots[1].equals(slots[3]) || slots[0].equals(slots[2]) && !slots[1].equals(slots[3])) {
                // Check if ship is horizontal or vertical
                if (Integer.valueOf(slots[1]) != Integer.valueOf(slots[3])) {
                    //Horizontal
                    if (Math.abs(Integer.valueOf(slots[1]) - Integer.valueOf(slots[3])) == size - 1) {
                        return checkIfClose(size, slots, board);
                    } else {
                        return false;
                    }
                } else {
                    int a = slots[0].charAt(0);
                    int b = slots[2].charAt(0);
                    if (Math.abs(Math.abs(a - b)) == size - 1) {
                        return checkIfClose(size, slots, board);
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }

        public void setCarrier(String[] carrier, Board board) {
            this.carrier = carrier;
            board.addBoat(carrier);
        }

        public void setBattleship(String[] battleship, Board board) {
            this.battleship = battleship;
            board.addBoat(battleship);
        }

        public void setSubmarine(String[] submarine, Board board) {
            this.submarine = submarine;
            board.addBoat(submarine);
        }

        public void setCruiser(String[] cruiser, Board board) {
            this.cruiser = cruiser;
            board.addBoat(cruiser);
        }

        public void setDestroyer(String[] destroyer, Board board) {
            this.destroyer = destroyer;
            board.addBoat(destroyer);
        }

        public String[] getCarrier() {
            return carrier;
        }

        public String[] getBattleship() {
            return battleship;
        }

        public String[] getSubmarine() {
            return submarine;
        }

        public String[] getCruiser() {
            return cruiser;
        }

        public String[] getDestroyer() {
            return destroyer;
        }
    }

    static String[] fixing(String line) {
        String[] split;
        String[] boat = new String[4];

        split = line.split(" ");
        if (split[0].length() > 2) {
            boat[0] = String.valueOf(split[0].charAt(0));
            boat[1] = "10";
        } else {
            boat[0] = String.valueOf(split[0].charAt(0));
            boat[1] = String.valueOf(split[0].charAt(1));
        }

        if (split[1].length() > 2) {
            boat[2] = String.valueOf(split[1].charAt(0));
            boat[3] = "10";
        } else {
            boat[2] = String.valueOf(split[1].charAt(0));
            boat[3] = String.valueOf(split[1].charAt(1));
        }
        return boat;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String sisse;
        char[] sisse1;

        Board board = new Board();
        System.out.println("Player 1, place your ships on the game field");
        board.printBoard();
        //String[] carrier = {"A", "1", "D", "1"};

        settingShip("Aircraft Carrier", 5, scanner, board);
        settingShip("Battleship", 4, scanner, board);
        settingShip("Submarine", 3, scanner, board);
        settingShip("Cruiser", 3, scanner, board);
        settingShip("Destroyer", 2, scanner, board);

        promptEnterKey();

        Board secondBoard = new Board();
        System.out.println("Player 2, place your ships on the game field");
        secondBoard.printBoard();

        settingShip("Aircraft Carrier", 5, scanner, secondBoard);
        settingShip("Battleship", 4, scanner, secondBoard);
        settingShip("Submarine", 3, scanner, secondBoard);
        settingShip("Cruiser", 3, scanner, secondBoard);
        settingShip("Destroyer", 2, scanner, secondBoard);

        promptEnterKey();

        //System.out.println("The game starts!");
        //board.printHiddenBoard();

        //System.out.println("Take a shot!");
        while (true) {
            // First player...
            secondBoard.printHiddenBoard();
            System.out.println("---------------------");
            board.printBoard();
            System.out.println("Player 1, it's your turn:");

            sisse = scanner.nextLine();
            sisse1 = sisse.toCharArray();

            while (!board.checkIfCorrect(sisse1[0], Integer.valueOf(sisse.split(String.valueOf(sisse1[0]))[1]))) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                sisse = scanner.nextLine();
                sisse1 = sisse.toCharArray();
            }
            secondBoard.shooting(sisse1[0], Integer.valueOf(sisse.split(String.valueOf(sisse1[0]))[1]));

            if (!secondBoard.checkShipsLeft()) {
                break;
            }

            promptEnterKey();

            // Second player...
            board.printHiddenBoard();
            System.out.println("---------------------");
            secondBoard.printBoard();
            System.out.println("Player 2, it's your turn:");

            sisse = scanner.nextLine();
            sisse1 = sisse.toCharArray();

            while (!board.checkIfCorrect(sisse1[0], Integer.valueOf(sisse.split(String.valueOf(sisse1[0]))[1]))) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                sisse = scanner.nextLine();
                sisse1 = sisse.toCharArray();
            }
            board.shooting(sisse1[0], Integer.valueOf(sisse.split(String.valueOf(sisse1[0]))[1]));
            if (!board.checkShipsLeft()) {
                break;
            }
            promptEnterKey();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void settingShip(String name, int len, Scanner scanner, Board board) {
        String[] boat;
        int asd = 0;
        System.out.println("Enter the coordinates of the " + name + " (" + len + " cells):");
        do {
            if (asd > 0) {
                System.out.println("Error! Wrong length of the " + name + "! Try again:");
            }
            boat = fixing(scanner.nextLine());
            asd++;
        } while (!board.player.checkShipFits(len, boat, board));
        if (name.equals("Aircraft Carrier")) {
            board.player.setCarrier(boat, board);
        } else if (name.equals("Battleship")) {
            board.player.setBattleship(boat, board);
        } else if (name.equals("Submarine")) {
            board.player.setSubmarine(boat, board);
        } else if (name.equals("Cruiser")) {
            board.player.setCruiser(boat, board);
        } else {
            board.player.setDestroyer(boat, board);
        }
        board.printBoard();
    }
}