
import java.util.*;

public class Lexer {
    public Lexer() {

    }

    public static void main(String[] args) throws Exception {
        Lexems lex = new Lexems();
        Queue<Token> tokens = new LinkedList();


        String test =   "a = 100 + 200 - 300; b = a + 100; b = 200 * 3; _while(a<100) { a = a + 50;}  _while(b<1000) { b = b + 100; }";

        //
        // ЛЕКСЕР
        //dxd

        lex.sort(test, tokens);


        for (Token item : tokens){
            System.out.println(item.type + " " + item.token);
        }

        Queue<Token> new_tokens = new LinkedList(tokens);

        //
        // ПАРСЕР
        //

        Parser parser = new Parser();
        parser.parse(tokens);


        //
        // ПРЕОБРАЗОВАНИЕ В ПОЛИЗ
        //

        Polis p = new Polis(new_tokens);
        List<Token> polis;
        polis = p.set_polis();

        for (int i = 0; i<polis.size(); i++){
            Token item = polis.get(i);
            System.out.println(i + " " + item.type + " " + item.token);
        }

        for (Token item : polis){

        }

        //
        // ВЫЧИСЛЕНИЕ ПОЛИЗА
        //

        CalcPolis cp = new CalcPolis(polis);

        cp.calc_polis();



    }
}
