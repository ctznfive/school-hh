import java.util.Scanner;
import java.lang.Character;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

class Main {

    private static HashMap<Integer, Integer> resultsHashMap = new HashMap<Integer, Integer>();
    private static DecimalFormat roundDF = new DecimalFormat("0.00");

    static int getPrecedence(char ch) {
        if      (ch == '>') return 1;
        else if (ch == '+' || ch == '-') return 2;
        else if (ch == '*') return 3;
        else return -1;
    }

    static String infixToPostfix(String expression) {
        expression = expression.replace("(-", "(0-");
        char firstChar = expression.charAt(0);
        if (firstChar == '-')
            expression = "0" + expression;
        if (Character.isDigit(firstChar))
            expression = "0+" + expression;

        Stack<Character> stack = new Stack<>();

        String expressionResult = new String("");

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isDigit(c))
                expressionResult += c;
            else if (c == '(')
                stack.push(c);
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    expressionResult += " " + stack.pop();
                stack.pop();
            } else {
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek()))
                    expressionResult += " " + stack.pop();
                stack.push(c);
                expressionResult += " ";
            }
            i++;
        }

        while (!stack.isEmpty()) {
            expressionResult += " ";
            expressionResult += stack.pop();
        }
        return expressionResult;
    }

    static int evaluatePostfix(String expression) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == ' ')
                continue;
            else if (Character.isDigit(ch)) {
                int n = 0;
                while (Character.isDigit(ch)) {
                    n = n*10 + (int)(ch-'0');
                    i++;
                    ch = expression.charAt(i);
                }
                i--;
                stack.push(n);
            } else {
                int value1 = stack.pop();
                int value2 = stack.pop();

                switch (ch) {
                    case '+':
                        stack.push(value2 + value1);
                        break;
                    case '-':
                        stack.push(value2 - value1);
                        break;
                    case '*':
                        stack.push(value2 * value1);
                        break;
                    case '>':
                        int res = (value2 > value1) ? 1 : 0;
                        stack.push(res);
                        break;
                }
            }
        }
        return stack.pop();
    }

    static int getFirstProbability(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (ch == 'd') {
                i++;
                ch = expression.charAt(i);
                int n = 0;

                while (Character.isDigit(ch)) {
                    n = n*10 + (int)(ch - '0');
                    i++;
                    if (i < expression.length())
                        ch = expression.charAt(i);
                    else
                        break;
                }
                return n;
            }
        }
        return -1;
    }

    static void calculateExpression(String expression) {
        int firstProbability = getFirstProbability(expression);
        if (firstProbability == -1) {
            int resultAfterCalculation = evaluatePostfix(infixToPostfix(expression));
            resultsHashMap.put(resultAfterCalculation,
                    resultsHashMap.getOrDefault(resultAfterCalculation, 0) + 1);
        } else {
            for (int i = 1; i <= firstProbability; i++) {
                String strReplace = expression.replaceFirst(
                        "d" + String.valueOf(firstProbability),
                        String.valueOf(i));
                calculateExpression(strReplace);
            }
        }
    }

    static int getSumOfHashMap(HashMap<Integer, Integer> resultsHashMap) {
        int sum = 0;
        for (int value : resultsHashMap.values())
            sum += value;
        return sum;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        calculateExpression(line);

        int sum = getSumOfHashMap(resultsHashMap);
        ArrayList<Integer> resultsSorted = new ArrayList<>(resultsHashMap.keySet());
        Collections.sort(resultsSorted);
        for (int num : resultsSorted) {
            double d = (double) resultsHashMap.get(num) * 100 / sum;
            System.out.println(num + " " + roundDF.format(d));
        }
    }
}

