import java.util.*;

public class CalcPolis {

    List<Token> polis;
    //Queue<Token> stack = new LinkedList<>();
    List<Var_table> var_table = new LinkedList<>();

    Stack<Token> stack = new Stack<>();

    CalcPolis(List<Token> _polis){
        polis = _polis;
    }

    //вычисление полиса
    void calc_polis(){

        Token var = new Token();
        boolean jmp = false;

        int polis_size = polis.size();
        for (int i = 0; i < polis_size; i++){

            Token item = polis.get(i);

            if(item.type == "VAR" || item.type == "NUM" || item.type == "JUMP"){
                stack.add(item);
            }

            if(item.type == "ASSIGN_OP"){
                var = stack.pop();
            }

            if(item.type == "OP"){
                ops(var.token, stack.pop().token, stack.pop().token, item.token);
            }

            if (item.type == "LOG_OP"){
                jmp = log_ops(item.token, stack.pop().token, stack.pop().token);
            }


            if (item.type == "IF"){

                System.out.println("\n" + !jmp + "---------------------------");
                for (Var_table itemm : var_table){
                    System.out.println(itemm.name + " " + itemm.value);
                }



                int jump = Integer.parseInt(stack.pop().token);

                if(jump > i){
                    if(!jmp){
                        i = jump;
                        System.out.println("\njump to i\n");
                    }
                }
                else {
                    if(jmp){
                        i = jump;
                        System.out.println("\njump to i\n");
                    }
                }

            }
        }

        System.out.println("\n");
        for (Var_table itemm : var_table){
            System.out.println(itemm.name + " " + itemm.value);
        }


    }

    //вычисление арифметического выражения
    void ops(String var, String first , String second, String op){


        double _first, _second;


        try {
            _first = Double.parseDouble(first);
        }catch (Exception var2){
            _first = find_var(first);
        }

        try {
            _second = Double.parseDouble(second);
        }catch (Exception var2){
            _second = find_var(second);
        }

        double result = 0;

        switch (op){
            case "+":
                result = _first + _second;
                break;
            case "-":
                result = _first - _second;
                break;
            case "*":
                result = _first * _second;
                break;
            case "/":
                result = _first / _second;
                break;

        }
        stack.add(new Token("NUM", String.valueOf(result)));
        if(contain_var(var))
            change_var(var, result);
        else
            var_table.add(new Var_table( var, result));
    }

    //вычисление логического выражения
    boolean log_ops(String op, String second, String first) {
        double _first, _second;

        try {
            _first = Double.parseDouble(first);
        } catch (Exception var2) {
            _first = find_var(first);
        }

        try {
            _second = Double.parseDouble(second);
        } catch (Exception var2) {
            _second = find_var(second);
        }

        boolean result = false;

        switch (op) {
            case "==":
                result = _second == _first;
                break;
            case ">":
                result = _first > _second;
                break;
            case "<":
                result = _first < _second;
                break;
            case ">=":
                result = _first >= _second;
                break;
            case "<=":
                result = _first <= _second;
                break;
            case "!=":
                result = _first != _second;
                break;
        }
        return result;

    }

    //возвращает значение переменной var из таблицы переменных
    double find_var(String var){

        for (Var_table item : var_table){
            if(item.name.equals(var))
                return item.value;
        }

        return 0;
    }

    // проверяет есть ли переменная var в таблице переменных
    boolean contain_var(String var){
        for (Var_table item : var_table){
            if(item.name.equals(var) )
                return true;
        }

        return false;
    }

    //меняет значение переменной var
    void change_var(String var, double value){
        for (Var_table item : var_table){
            if(item.name.equals(var))
                item.value = value;
        }
    }
}
