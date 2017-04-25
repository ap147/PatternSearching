//Amarjot Parmar (1255668)
//http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou

public class REcompile
{
    private static Character ch [] = new Character[20];
    private static Integer next1 [] = new Integer[20];
    private static Integer next2 [] = new Integer[20];
    private static Integer state=1;
    private static String [] regex;
    private static int index = 0;

    private static boolean bracket = false;
    private static boolean customestartState = false;

    private static int count;
    private static int temp;
    private static int startState = 1;
    private static int lastStartState = 1;
    private static int lastlastStartState =1;
    private static final char nulll = '\u0012';
    private static final char empty = '\u0012';
    private static final char consumeNSkip = '\u0011';

    public static void main(String[] args)
    {
        regex = args[0].split("");
        parse();
        // System.out.println(isvocab(p[0]));
    }
    private static void parse()
    {
        int initial;

        initial=expression();// <-

        //if( p[j].equals("1") ) error(); // In C, zero is false, not zero is true
        set_state(state,nulll,0,0);

        ch[0] = nulll;
        next1[0]= startState;
        next2[0] =startState;


      //  next1[startState] = lastStartState;
       // System.out.println("LAAAAASSST " + lastlastStartState);

       // next2[lastlastStartState] = next2[lastlastStartState]-1;
        System.err.println("STATEs" + state);
        dump();
        printArrays();

    }
    private static void printStringArray()
    {
        for(int x = index; x < regex.length; x++)
        {
            System.err.print(regex[x]);
        }

    }
    private static int expression()
    {
        System.err.print("expression (" );
        printStringArray();
        System.err.println(") ");
        int r;

        r=term();
        if(index <= regex.length-1) {
            if (isvocab(regex[index]) || regex[index].equals("(")) expression();
        }
        return(r);
    }

    private static int term()
    {
        System.err.print("term (" );
        printStringArray();
        System.err.println(") ");
        int r;
        int t1, t2, f;

        f = state - 1;
        r = t1 = factor();

        // printArrays();
        if (index <= regex.length - 1)
        {
            //escaped characters (i.e. symbols preceded by \)
            if (index < regex.length && regex[index].equals("\\") && !bracket)
            {
                index++;
                char x = regex[index].charAt(0);
                set_state(state, x, state + 1, state + 1);
                index++;
                state++;
                term();
            }
            //parentheses (i.e. the most deeply nested regexps have the highest precedence)
            //list of alternative literals (i.e. [ and ])
            if(index < regex.length && regex[index].equals("["))
            {
                if(index+1 < regex.length && regex[index+1].equals("]"))
                {
                    error();
                }
                bracket = true;
                index++;
                temp= index;
            }
            else if(index < regex.length && regex[index].equals("]"))
            {
                bracket = false;
                System.err.println("BRACKETS ENDED");
                index++;
            }
            if(bracket & index > temp)
            {
                System.err.print(index + "            ---------------- INDEX");
                if(!customestartState&&index ==2)
                {
                    startState = state ;
                }
                customestartState = true;

                if (next1[f] == next2[f])
                    next2[f] = state;
                next1[f] = state;
                f = state - 1;

                r = state;
                state++;
                t2 = term();

                set_state(r, empty, t1, t2);
                if (next1[f] == next2[f])
                    next2[f] = state;
                next1[f] = state;
            }
            //repetition operators (i.e. * and ?)
            if (index < regex.length && regex[index].equals("*") && !bracket)
            {
                int prevState = state - 1;
                set_state(state, ch[prevState], state-1, t1);//state-1);
                next1[prevState] = state+1;
                next2[prevState] = state;
                ch[prevState] = empty;

                index++;
                r = state;
                state++;
                term();
            }
            if (index < regex.length && regex[index].equals("?") && !bracket)
            {


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
                // term();
            }
            // concatenation
            if (index < regex.length && regex[index].equals(".") && !bracket)
            {
                set_state(state, consumeNSkip, state + 1, state + 1);
                index++;
                r = state;
                state++;
                //term();
            }
            //alternation (i.e. |)
            if (index < regex.length && regex[index].equals("|") && !bracket)
            {

                if(!customestartState)
                {
                    startState = state;
                }
                customestartState = true;
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

                // term();
                /*
                lastlastStartState = state;
               if(!customestartState)
                {
                    count++;
                    lastStartState = startState;
                    startState = state;
                    System.err.println("Previous Start State : " + lastStartState + " New Start State :" + state);
                }

                customestartState = true;
                /*
                if(doit())
                {
                    if (next1[f] == next2[f])
                        next2[f] = state;
                    next1[f] = state;
                }
                */

                /*

                f = state - 1;
                index++;
                r = state;
                state++;
                t2 = term();
                */
                /*
                //set_state(r, empty,lastStartState,lastStartState);
                if(count == 1) {

                    set_state(r, empty, lastStartState, t2);// t1, t2);
                    pintState(r);
                    System.out.println("----------------------------------") ;
                }
                else
                {
                    set_state(r, empty, t1, t2);// t1, t2);
                }


                    set_state(r, empty, lastStartState,t2 +1);//t1, t2);


                if (next1[f] == next2[f])
                    next2[f] = state;
                next1[f] = state;
                */

            }
        }

        return(r);
    }
    private static boolean doit()
    {
        System.err.println(startState + "  " + (state -2));
        if(startState == state -2)
        {
            count = 1;
            System.err.println("----------------- true");
            return true;
        }
        System.err.println("----------------- false");
        return false;
    }
    private static int factor() {
        System.err.print("factor (" );
        printStringArray();
        System.err.println(") ");
        int r = 0;

        if (index < regex.length) {
            if (isvocab(regex[index])) {
                char x = regex[index].charAt(0);
                set_state(state, x, state + 1, state + 1);
                index++;
                r = state;

                state++;
                if(!bracket)
                {
                    customestartState = false;
                }
            }
            else if (regex[index].equals("(")) {
                index++;
                if (regex[index].equals(")"))
                {
                    error();
                }
                else
                {
                    r = expression();
                    System.err.println("MAGIC ?? :" + r);
                    if (regex[index].equals(")"))
                        index++;
                    else
                        error();
                }
            } else {
                error();
            }


        }
        return (r);
    }

    private static boolean isvocab(String c)
    {
        if(!bracket)
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

    private static void pintState(int s)
    {
        System.err.println( s +" | " +ch[s] + " " + next1[s] + " " + next2[s]);
    }
    private static void printArrays()
    {
        System.err.println();
        System.err.println("s | ch 1 2");
        for(int x =0; x< state; x++)
        {


                System.err.println(x + " | " + ch[x] + " " + next1[x] + " " + next2[x]);

        }
        System.err.println(state +" | " +ch[state] + " " + next1[state] + " " + next2[state]);
    }
    private static void dump ()
    {
        for(int x =0; x< state; x++)
        {
            if(!next1[x].equals(null))
           {
                System.out.println(x +" " +ch[x] + " " + next1[x] + " " + next2[x]);
            }
        }
        System.out.println(state +" " +ch[state] + " " + next1[state] + " " + next2[state]);
    }
    private static void error()
    {
        System.out.println("Error");
        //printArrays();
    }
}





