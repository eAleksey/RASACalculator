/*
 * @(#)RasaCalculator.java        1.1 2023/05/14
 *
 * Copyright (c) Aleksei Trokhin
 * a.trokhin@gmail.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Aleksei Trokhin ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Aleksei Trokhin.
 */

import java.util.*;

/**
 *
 This class .
 *
 * @version
1.1 14 May 2023   * @author
Aleksey Trokhin */
public class RasaCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        String output = calc(input);
        System.out.print(output);
    }
    public static String calc(String input) {
        try {
            String[] strings = input.split("[ ]+");
            boolean validInputArgument = isValidInputArgument(strings);
            int[] numbers = new int[2];
            if (validInputArgument) {
                numbers[0] = romanStringToArabicValue(strings[0]);
                numbers[1] = romanStringToArabicValue(strings[2]);
            } else {
                numbers[0] = Integer.parseInt(strings[0]);
                numbers[1] = Integer.parseInt(strings[2]);
            }
            if ((numbers[0] > 10) || (numbers[1] > 10) || (numbers[0] < 1) || (numbers[1] < 1)) {
                throw new IllegalArgumentException("Один из аргументов либо меньше 1, либо больше 10!");
            }
            int result = 0;
            switch (strings[1]) {
                case "+":
                    result = numbers[0] + numbers[1];
                    break;
                case "-":
                    result = numbers[0] - numbers[1];
                    break;
                case "*":
                    result = numbers[0] * numbers[1];
                    break;
                case "/":
                    result = (int)(numbers[0] / numbers[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Неправильный оператор!");
            }
            String output = " ";
            if (validInputArgument) {
                if (result > 0) {
                    output = arabicNumberToRomanSimbols(result);
                    return output;
                } else {
                    throw new IllegalArgumentException("Отрицательное или нулевое Римское Число!");
                }
            } else {
                    output = Integer.toString(result);
                    return output;
            }
        } catch (IllegalArgumentException i) {
            String output = "throws Exception";
            return output;
        }
    }
    private static boolean isValidInputArgument(String[] strings) throws IllegalArgumentException {
        if (strings.length == 3 && strings[0].matches("^(X|IX|IV|VI{0,3}|I{1,3})$")
            && strings[2].matches("^(X|IX|IV|VI{0,3}|I{1,3})$")) {
            return true;
        } else if (strings.length == 3 && strings[0].matches("[0-9]+")
                   && strings[2].matches("[0-9]+")) {
            return false;
        } else {
            throw new IllegalArgumentException("Неправильные аргументы или их количество!");
        }
    }
    private static int romanStringToArabicValue(String string) {
        int previous = 0;
        int result = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            int current = romanSimbolToArabicNumber(string.charAt(i));
            if (current >= previous) {
                result = result + current;
            } else {
                result = result - current;
            }
            previous = current;
        }
        return result;
    }
    private static int romanSimbolToArabicNumber(char ch) {
        int result = 0;
        switch (ch) {
            case 'I':
                result = 1;
                break;
            case 'V':
                result = 5;
                break;
            case 'X':
                result = 10;
                break;
            default:
                result = -1;
                break;
        }
        return result;
    }
    private static String arabicNumberToRomanSimbols(int number) {
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSimbols = {"C", "CX", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (number > 0) {
            if (number - values[i] >= 0) {
                stringBuilder.append(romanSimbols[i]);
                number = number - values[i];
            } else {
                i++;
            }
        }
        return stringBuilder.toString();
    }
}
