//Amarjot Parmar (1255668)
//Joseph Boyd (1264974)
//http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou

public class REcompile
{
    //Where FST is stored, ran out of time to implement lists
    //The mismatch needed to move onto next1 or next2
    private static Character ch [] = new Character[21474];
    private static Integer next1 [] = new Integer[21474];
    private static Integer next2 [] = new Integer[21474];
    //default starting state
    private static Integer state=1;
    private static String [] regex;
    //Pointer to move around regex
    private static int index = 0;

    private static boolean bracket = false;
    private static boolean customestartState = false;

    private static int temp;
    private static int startState = 1;
    private static final char nulll = '\u0012';
    private static final char empty = '\u0012';
    private static final char consumeNSkip = '\u0011';

    public static void main(String[] args)
    {
        regex = args[0].split("");
        parse();
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

        System.err.println("STATEs" + state);
        dump();
        printArrays();
    }
    //deals with a expresion , called when ( starting or when program run at first
    private static int expression()
    {
        System.err.print("expression (" );
        printStringArray();
        System.err.println(") ");
        int r;

        r=term();
        if(index <= regex.length-1)
        {
            if (isvocab(regex[index]) || regex[index].equals("(")) expression();
        }
        return(r);
    }
    //Deals with literals
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
                //if [] then invalid
                if(index+1 < regex.length && regex[index+1].equals("]"))
                {
                    error();
                }
                bracket = true;
                index++;
                temp= index;
            }
            //When ] comes around after [abc]
            else if(index < regex.length && regex[index].equals("]"))
            {
                bracket = false;

                index++;
            }
            //if [abc]
            if(bracket & index > temp)
            {
                //When "[abc]"
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
                //Creating state which will link to state to loop around with
                set_state(state, ch[prevState], state-1, t1);//state-1);1
                //creating a branching state which will either link to a (a*) and next state
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
                //Creating a branching state which goes to a or moves on and a state which just moves on "a?"
                int prevState = state - 1;
                set_state(state, ch[prevState], next1[prevState] + 2, next2[prevState] + 2);

                next2[prevState] = state;
                ch[prevState] = empty;

                state++;
                set_state(state, empty, state + 1, state + 1);

                next1[prevState] = state;

                index++;
                r = state;
                state++;
            }
            // concatenation
            if (index < regex.length && regex[index].equals(".") && !bracket)
            {
                //creates a state with special symbol and moves onto next index
                set_state(state, consumeNSkip, state + 1, state + 1);
                index++;
                r = state;
                state++;
            }
            //alternation (i.e. |)
            if (index < regex.length && regex[index].equals("|") && !bracket)
            {
                //Correcting the start state so you can branch around
                if(!customestartState)
                {
                    startState = state;
                }
                customestartState = true;
                //a (a|b)
                if (next1[f] == next2[f])
                    next2[f] = state;
                next1[f] = state;
                f = state - 1;
                index++;
                r = state;
                state++;
                t2 = term();
                //branching state (a|b)
                set_state(r, empty, t1, t2);
                //b (a|b)
                if (next1[f] == next2[f])
                    next2[f] = state;
                next1[f] = state;
            }
        }
        return(r);
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
                //Making sure regex isnt invalid ()
                if (regex[index].equals(")"))
                {
                    error();
                }
                else
                {
                    //to make a(b)*c work , i would have to link end state of a to b's starting state which would be a branching state, and link that to c and same for ? etc
                    r = expression();
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
    private static void printStringArray()
    {
        for(int x = index; x < regex.length; x++)
        {
            System.err.print(regex[x]);
        }

    }
    private static void pintState(int s)
    {
        System.err.println( s +" | " +ch[s] + " " + next1[s] + " " + next2[s]);
    }
    //prints all array in nice format
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
    //Prints all arrays for REsearch
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
    }
}





