//Amarjot Parmar (1255668)
//http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou

/*

    any symbol that does not have a special meaning (as given below) is a literal that matches itself
    . is a wildcard symbol that matches any literal
    adjacent regexps are concatenated to form a single regexp
    * indicates closure (zero or more occurrences) on the preceding regexp
    ? indicates that the preceding regexp can occur zero or one time
    | is an infix alternation operator such that if r and e are regexps, then r|e is a regexp that matches one of either r or e
    ( and ) may enclose a regexp to raise its precedence in the usual manner; such that if e is a regexp, then (e) is a regexp and is equivalent to e. e cannot be empty.
    [ and ] may enclose a list of literals and matches one and only one of the enclosed literals. Any special symbols in the list lose their special meaning, except ] which must appear first in the list if it is a literal. The enclosed list cannot be empty.
    \ is an escape character that matches nothing but indicates the symbol immediately following the backslash loses any special meaning and is to be interpretted as a literal symbol
    operator precedence is as follows (from high to low):
        escaped characters (i.e. symbols preceded by \)
        parentheses (i.e. the most deeply nested regexps have the highest precedence)
        list of alternative literals (i.e. [ and ])
        repetition operators (i.e. * and ?)
        concatenation
        alternation (i.e. |)

 */
public class REcompile
{   static Character ch [] = new Character[20];
    static Integer next1 [] = new Integer[20];
    static Integer next2 [] = new Integer[20];
    static Integer state=0;
    static String [] regex;
    static int index = 0;
    static boolean justDoIt = false;
    static int count;
    static final char empty = '\u0012';
    static final char consumeNSkip = '\u0011';
    public static void main(String[] args)
    {
        System.err.println("Expersion : " + args[0]);
        regex = args[0].split("");
        parse();
        // System.out.println(isvocab(p[0]));
    }
    private static void parse()
    {
        System.err.println("par");
        int initial;

        initial=expression();// <-
        System.err.println("ENDING ");

        //if( p[j].equals("1") ) error(); // In C, zero is false, not zero is true
        set_state(state,empty,0,0);
        dump();
        printArrays();

    }

    private static int expression()
    {
        System.err.println("exp " +regex[index]+ " isvocab : " +   isvocab(regex[index]));
        int r;

        r=term();
        if(index <= regex.length-1) {
            if (isvocab(regex[index]) || regex[index].equals("[")) expression();
        }
        return(r);
    }

    private static int term()
    {
        int r;


            System.err.println("term " + regex[index] + " isvocab : " + isvocab(regex[index]));

            int t1, t2, f;
            f = state - 1;
            r = t1 = factor();
            // printArrays();
            System.err.println(regex.length);
            if (index <= regex.length - 1) {
                if (index < regex.length && regex[index].equals("*"))
                {
                    int prevState = state - 1;

                    set_state(state, ch[prevState], next1[prevState] + 1, next2[prevState] + 2);

                    next2[prevState] = state;
                    ch[prevState] = empty;

                    state++;

                    set_state(state, empty, state + 1, state -1 );

                    next1[prevState] = state;

                    index++;
                    r = state;
                    state++;
                }
                if (index < regex.length && regex[index].equals("|")) {
                    if (next1[f] == next2[f])
                        next2[f] = state;
                    next1[f] = state;
                    f = state - 1;
                    index++;
                    r = state;
                    state++;
                    t2 = term();
                    set_state(r, empty, t1, t2);
                    if (next1[f] == next2[f])
                        next2[f] = state;
                    next1[f] = state;
                }
                if (index < regex.length && regex[index].equals("?")) {
                    int prevState = state - 1;
                    //5
                    set_state(state, ch[prevState], next1[prevState] + 2, next2[prevState] + 2);
                    //4
                    next2[prevState] = state;
                    ch[prevState] = empty;

                    state++;
                    //7
                    set_state(state, empty, state + 1, state + 1);

                    next1[prevState] = state;

                    index++;
                    r = state;
                    state++;
                }

                if (index < regex.length && regex[index].equals("\\")) {
                    index++;
                    char x = regex[index].charAt(0);
                    set_state(state, x, state + 1, state + 1);
                    index++;
                    state++;
                }
            }

        return(r);
    }

    private static int factor()
    {
        //System.err.println("factor : " +regex[index]+ " isvocab : " +   isvocab(regex[index]));

        int r =0;
        System.err.println(index);
        if(isvocab(regex[index]) || justDoIt)
        {
            char x = regex[index].charAt(0);
            set_state(state, x, state + 1, state + 1);
            index++;
            r = state;
            state++;
        }
        else
        if(regex[index].equals("[")) {
            printArrays();
            index++;
            if (regex[index].equals("]")) {
                System.err.println("------------- DO MAGIC HERE ([]) ");
            }
            else
            {
                justDoIt = true;
                while(!regex[index].equals("]"))
                {
                    System.err.println("----------------------Index : " + index);
                    count++;

                    printArrays();
                    if(count >= 2)
                    {
                        System.err.println("term2 " + index + " isvocab : " + isvocab(regex[5]));
                        int t1, t2, f;
                        f = state - 1;
                        r = t1 = factor();
                        if (next1[f] == next2[f])
                            next2[f] = state;
                        next1[f] = state;
                        f = state - 1;
                        //index++;
                        r = state;
                        state++;
                        t2 = term();
                        set_state(r, '\u0000', t1, t2);
                        if (next1[f] == next2[f])
                            next2[f] = state;
                        next1[f] = state;
                    }

                }
                justDoIt = false;
            }


        }
            /*
            index++; r=expression();// <-
            if(regex[index].equals("]"))
                index++;
            else
                error();
                */

        else
            error();

        //print();
        return(r);
    }

    private static boolean isvocab(String c)
    {
        if(!justDoIt)
        {
            char x = c.charAt(0);
            return Character.isAlphabetic(x);
        }

        return true;
    }

    private static void set_state(int s, char c, int n1, int n2)
    {
        ch[s]=c;next1[s]=n1;next2[s]=n2;
    }
    private static void print()
    {
        System.out.println();
        for(int x = index; x < regex.length; x++)
        {
            System.out.print(regex[x]);
        }
        System.out.println();
    }
    private static void pintState(int s)
    {
        System.out.println( s +" | " +ch[s] + " " + next1[s] + " " + next2[s]);
    }
    private static void printArrays()
    {
        System.err.println();
        System.err.println("s | ch 1 2");
        for(int x =0; x< state+1; x++)
        {
            if(!next1[x].equals(null))
            {
                System.err.println(x +" | " +ch[x] + " " + next1[x] + " " + next2[x]);
            }

        }
    }
    private static void dump ()
    {
        for(int x =0; x< state+1; x++)
        {
            if(!next1[x].equals(null))
            {
                System.out.println(x +" " +ch[x] + " " + next1[x] + " " + next2[x]);
            }

        }
    }
    private static void error()
    {
        System.out.println("Error");
        printArrays();
    }
}




