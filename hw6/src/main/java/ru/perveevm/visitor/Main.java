package ru.perveevm.visitor;

import ru.perveevm.visitor.token.Token;
import ru.perveevm.visitor.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer(System.in);
        ParseVisitor parseVisitor = new ParseVisitor();
        PrintVisitor printVisitor = new PrintVisitor();
        CalcVisitor calcVisitor = new CalcVisitor();

        List<Token> tokens;
        try {
            tokens = tokenizer.tokenize();
        } catch (IOException e) {
            System.out.println("Error happened while reading expression: " + e.getMessage());
            return;
        }

        System.out.println("Original tokens:");
        printVisitor.print(tokens, System.out);

        List<Token> polishTokens = parseVisitor.convert(tokens);
        System.out.println("Polish-notation tokens:");
        printVisitor.print(tokens, System.out);

        System.out.println("Result:");
        System.out.println(calcVisitor.calculate(polishTokens));
    }
}
