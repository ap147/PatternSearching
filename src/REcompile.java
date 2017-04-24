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

    private static int startState = 0;

    private static final char empty = '\u0012';
    private static final char consumeNSkip = '\u0011';

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

        if(!customestartState)
        {
            startState = 1;
        }
        ch[0] = ch[startState];
        next1[0]= next1[startState];
        next2[0] =next2[startState];

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
        int t1, t2, f;

        f = state - 1;
        r = t1 = factor();
        // printArrays();
        System.err.println(regex.length);
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
            if(bracket)
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
            }
            //repetition operators (i.e. * and ?)
            if (index < regex.length && regex[index].equals("*") && !bracket)
            {
                int prevState = state - 1;
                set_state(state, ch[prevState], state-1, state-1);
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

                    //term();

            }
        }
        System.out.println("Current STATES : "+state);
        return(r);
    }

    private static int factor() {
        int r = 0;

        if (index < regex.length) {
            System.err.println("----------------------------regex[index]" + regex[index]);
            if (isvocab(regex[index])) {

                char x = regex[index].charAt(0);
                set_state(state, x, state + 1, state + 1);
                index++;
                r = state;
                state++;
            }
            if (regex[index].equals("(")) {
                index++;
                r = expression();
                if (regex[index].equals(")"))
                    index++;
                else
                    error();
            } else {
                error();
            }

        }
        return (r);
    }

    private static boolean isvocab(String c)
    {
        System.out.println("isvocab ? : " + c);
        if(!bracket)
        {
            char x = c.charAt(0);
            return Character.isAlphabetic(x);
        }
        else if(bracket && regex[index].equals("]"))
        {
            return false;
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





