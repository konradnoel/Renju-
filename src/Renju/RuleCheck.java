package Renju;

import java.util.List;
import java.util.Objects;

public class RuleCheck {
    //All the checks return how many there are in a consecutive line, starting from the field the player currently clicked on
    //Sideways checkings
    public static int checkRight(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((x + 1) != rowLength && Objects.equals(PlayBoard.get(y).get(x + 1), color))
            return checkRight(PlayBoard, y, x + 1, color, counter, rowLength);
        return counter;
    }

    public static int checkLeft(List<List<String>> PlayBoard, int y, int x, String color, int counter) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((x - 1) != -1 && Objects.equals(PlayBoard.get(y).get(x - 1), color))
            return checkLeft(PlayBoard, y, x - 1, color, counter);
        return counter;
    }

    //Vertical checkings
    public static int checkUp(List<List<String>> PlayBoard, int y, int x, String color, int counter) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y - 1) != -1 && Objects.equals(PlayBoard.get(y - 1).get(x), color))
            return checkUp(PlayBoard, y - 1, x, color, counter);
        return counter;
    }

    public static int checkDown(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y + 1) != rowLength && Objects.equals(PlayBoard.get(y + 1).get(x), color))
            return checkDown(PlayBoard, y + 1, x, color, counter, rowLength);
        return counter;
    }

    //Diagonal checkings

    public static int checkRightDownDiag(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y + 1) != rowLength && (x + 1) != rowLength && Objects.equals(PlayBoard.get(y + 1).get(x + 1), color))
            return checkRightDownDiag(PlayBoard, y + 1, x + 1, color, counter, rowLength);
        return counter;
    }

    public static int checkLeftDownDiag(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y + 1) != rowLength && x - 1 != -1 && Objects.equals(PlayBoard.get(y + 1).get(x - 1), color))
            return checkLeftDownDiag(PlayBoard, y + 1, x - 1, color, counter, rowLength);
        return counter;
    }

    public static int checkRightUpDiag(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y - 1) != -1 && x + 1 != rowLength && Objects.equals(PlayBoard.get(y - 1).get(x + 1), color))
            return checkRightUpDiag(PlayBoard, y - 1, x + 1, color, counter, rowLength);
        return counter;
    }

    public static int checkLeftUpDiag(List<List<String>> PlayBoard, int y, int x, String color, int counter) {
        if (Objects.equals(PlayBoard.get(y).get(x), color)) counter += 1;
        if ((y - 1) != -1 && x - 1 != -1 && Objects.equals(PlayBoard.get(y - 1).get(x - 1), color))
            return checkLeftUpDiag(PlayBoard, y - 1, x - 1, color, counter);
        return counter;
    }

    //Joint functions for easier management, they return true if there was 5 consecutive fields in any direction.
    public static boolean checkSide(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        return checkRight(PlayBoard, y, x, color, counter, rowLength) + checkLeft(PlayBoard, y, x, color, counter) - 1 >= 5;
    }

    public static boolean checkVertical(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        return checkUp(PlayBoard, y, x, color, counter) + checkDown(PlayBoard, y, x, color, counter, rowLength) - 1 >= 5;
    }

    public static boolean checkDiagonal(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (checkLeftUpDiag(PlayBoard, y, x, color, counter) + checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 >= 5)
            return true;
        return checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength) + checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 >= 5;
    }

    //The main function to check if the player has got 5 consecutive fields in any direction.
    public static boolean checkAll(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength) {
        if (checkSide(PlayBoard, y, x, color, counter, rowLength)) return true;
        if (checkVertical(PlayBoard, y, x, color, counter, rowLength)) return true;
        return checkDiagonal(PlayBoard, y, x, color, counter, rowLength);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks to see if the lines have open ends. (The requirement for a fork to be illegal is that it has to be open
    public static boolean isOpenUp(List<List<String>> PlayBoard, int y, int x) {
        if (y - 1 >= 0) {
            if (Objects.equals(PlayBoard.get(y - 1).get(x), " ")) return true;
            if (Objects.equals(PlayBoard.get(y - 1).get(x), "B")) return isOpenUp(PlayBoard, y - 1, x);
        }
        return false;
    }

    public static boolean isOpenDown(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        if (y + 1 != rowLength) {
            if (Objects.equals(PlayBoard.get(y + 1).get(x), " ")) return true;
            if (Objects.equals(PlayBoard.get(y + 1).get(x), "B")) return isOpenDown(PlayBoard, y + 1, x, rowLength);
        }
        return false;
    }

    public static boolean isOpenLeft(List<List<String>> PlayBoard, int y, int x) {
        if (x - 1 >= 0) {
            if (Objects.equals(PlayBoard.get(y).get(x - 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y).get(x - 1), "B")) return isOpenLeft(PlayBoard, y, x - 1);
        }
        return false;
    }

    public static boolean isOpenRight(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        if (x + 1 != rowLength) {
            if (Objects.equals(PlayBoard.get(y).get(x + 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y).get(x + 1), "B")) return isOpenRight(PlayBoard, y, x + 1, rowLength);
        }
        return false;
    }

    public static boolean isOpenUpLeft(List<List<String>> PlayBoard, int y, int x) {
        if (y - 1 >= 0 && x - 1 >= 0) {
            if (Objects.equals(PlayBoard.get(y - 1).get(x - 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y - 1).get(x - 1), "B")) return isOpenUpLeft(PlayBoard, y - 1, x - 1);
        }
        return false;
    }

    public static boolean isOpenUpRight(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        if (y - 1 >= 0 && x + 1 != rowLength) {
            if (Objects.equals(PlayBoard.get(y - 1).get(x + 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y - 1).get(x + 1), "B")) return isOpenUpRight(PlayBoard, y - 1, x + 1, rowLength);
        }
        return false;
    }

    public static boolean isOpenDownLeft(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        if (y + 1 != rowLength && x - 1 >= 0) {
            if (Objects.equals(PlayBoard.get(y + 1).get(x - 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y + 1).get(x - 1), "B")) return isOpenDownLeft(PlayBoard, y + 1, x - 1, rowLength);
        }
        return false;
    }

    public static boolean isOpenDownRight(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        if (y + 1 != rowLength && x + 1 != rowLength) {
            if (Objects.equals(PlayBoard.get(y + 1).get(x + 1), " ")) return true;
            if (Objects.equals(PlayBoard.get(y + 1).get(x + 1), "B"))
                return isOpenDownRight(PlayBoard, y + 1, x + 1, rowLength);
        }
        return false;
    }

    public static boolean isOpenSide(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        return isOpenRight(PlayBoard, y, x, rowLength) && isOpenLeft(PlayBoard, y, x);
    }

    public static boolean isOpenVertical(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        return isOpenUp(PlayBoard, y, x) && isOpenDown(PlayBoard, y, x, rowLength);
    }

    public static boolean isOpenUpLeftDownRight(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        return isOpenUpLeft(PlayBoard, y, x) && isOpenDownRight(PlayBoard, y, x, rowLength);
    }

    public static boolean isOpenUpRightDownLeft(List<List<String>> PlayBoard, int y, int x, int rowLength) {
        return (isOpenUpRight(PlayBoard, y, x, rowLength) && isOpenDownLeft(PlayBoard, y, x, rowLength));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Checks if there is an illegal fork for black
    public static boolean checkFork(List<List<String>> PlayBoard, int y, int x, String color, int counter, int rowLength, int howMuch){
        //If there is 3 or 4 in a horizontal line, depending on the value of howMuch, and if it is open sideways
        if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength))-1 == howMuch && isOpenSide(PlayBoard, y, x, rowLength)) {
                for (int i = 0; i < howMuch; i++) {
                    if (Objects.equals(PlayBoard.get(y).get(x), color)) {
        //Check if it's a black square, if not, then the cycle breaks

                        //Check the field if it has any crossing vertically, or diagonally, and if it is open in that direction
                        if ((checkUp(PlayBoard, y, x, color, counter) + checkDown(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                        if ((checkLeftUpDiag(PlayBoard, y, x, color, counter) + checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                        if ((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength) + checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;
        //Guard that we don't over-index
                        if(x + 1 != rowLength) x+= 1;

                    } else break; //The break if it's a white or empty square
                }

                //Resetting the position to the original square
                x = x - (howMuch - 1);

                //Doing all the above in the opposite direction

                for (int i = 0; i < howMuch; i++) {
                    if (Objects.equals(PlayBoard.get(y).get(x), color)) {

                        if ((checkUp(PlayBoard, y, x, color, counter) + checkDown(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                        if ((checkLeftUpDiag(PlayBoard, y, x, color, counter) + checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                        if ((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength) + checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength) - 1 == howMuch)
                                && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;

                        if(x - 1 >= 0) x-= 1;
                    } else break;
                }
            }

        //Checking if the square now has a total of 3 or 4 vertically instead of horizontally
        else if((checkUp(PlayBoard, y, x, color, counter)+checkDown(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch) && isOpenVertical(PlayBoard, y, x, rowLength)){

            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){
                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkLeftUpDiag(PlayBoard, y, x, color, counter)+checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                    if((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength)+checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;

                    if(y+1 != rowLength) y+= 1;

                } else break;
            }
            y = y - (howMuch-1);
            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){
                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkLeftUpDiag(PlayBoard, y, x, color, counter)+checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                    if((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength)+checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;

                    if(y-1 >= 0) y-= 1;

                } else break;
            }
        }

        //Now checking for the diagonal directions, if there is 3 or 4, and if it's open in that specific direction
        else if((checkLeftUpDiag(PlayBoard, y, x, color, counter)+checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch) && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)){
            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){

                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkUp(PlayBoard, y, x, color, counter)+checkDown(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                    if((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength)+checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;

                    if(y+1 != rowLength && x+1 != rowLength) {y += 1; x += 1;}

                } else break;
            }

            y = y - (howMuch-1);
            x = x - (howMuch-1);

            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){

                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkUp(PlayBoard, y, x, color, counter)+checkDown(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                    if((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength)+checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)) return true;

                    if(y-1 >= 0 && x-1 >= 0) {y -= 1; x -= 1;}

                } else break;
            }
        }

        else if((checkRightUpDiag(PlayBoard, y, x, color, counter, rowLength)+checkLeftDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                && isOpenUpRightDownLeft(PlayBoard, y, x, rowLength)){
            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){

                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkUp(PlayBoard, y, x, color, counter)+checkDown(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                    if((checkLeftUpDiag(PlayBoard, y, x, color, counter)+checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                    if(y-1 >= 0 && x + 1 != rowLength){y -= 1; x += 1;}
                } else break;
            }

            y = y + (howMuch-1);
            x = x - (howMuch-1);

            for(int i = 0; i < howMuch; i++){
                if(Objects.equals(PlayBoard.get(y).get(x), color)){

                    if((checkLeft(PlayBoard, y, x, color, counter)+checkRight(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenSide(PlayBoard, y, x, rowLength)) return true;

                    if((checkUp(PlayBoard, y, x, color, counter)+checkDown(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenVertical(PlayBoard, y, x, rowLength)) return true;

                    if((checkLeftUpDiag(PlayBoard, y, x, color, counter)+checkRightDownDiag(PlayBoard, y, x, color, counter, rowLength)-1 == howMuch)
                            && isOpenUpLeftDownRight(PlayBoard, y, x, rowLength)) return true;

                    if(y+1 != rowLength && x - 1 >= 0){y += 1; x -= 1;}
                } else break;
            }
        }
        return false;
    }

    //Checks if Black has reached 6 black fields, which is also illegal
    public static boolean checkOverline(List<List<String>> PlayBoard, int y, int rowLength){
        int counter = 0;
        for(int i = 0; i < rowLength; i++){
            if(Objects.equals(PlayBoard.get(y).get(i), "B")) counter++;
            if(6 <= counter) return true;
        }
        return false;
    }

    //A joint function for easy management, checks for any possibilites of forbidden moves
    public static boolean checkBlackForbiddens(List<List<String>> PlayBoard, int y, int x, int rowLength){
        if(RuleCheck.checkFork(PlayBoard, y, x, ("B"), 0, rowLength, 3)) return true;
        else if(RuleCheck.checkFork(PlayBoard, y, x, ("B"), 0, rowLength, 4)) return true;
        else return checkOverline(PlayBoard, y, rowLength);
    }
}