//Amarjot Parmar (1255668)

public class REcompile
{

    /*
    the state-number,
    the input-symbol this state must match (or indication it is a branching state, or similar),
     and two numbers indicating the two possible next states if a match is made.
     */
    static String [] p;
    static int j = 0;
    public static void main(String[] args)
    {
        System.err.println("Expersion : " + args[0]);
        p = args[0].split("");
        parse();
       // System.out.println(isvocab(p[0]));
    }
    public static void expression()
    {
        System.err.println("exp " +p[j]+ " isvocab : " +   isvocab(p[j]));
        term();
        System.err.println("exp " + p[j] + " isvocab : " + isvocab(p[j]));
        if(isvocab(p[j])||p[j].equals('(')) {

            expression();
        }
    }

    private static void term()
    {
        System.err.println("term "+ p[j]+ " isvocab : " +   isvocab(p[j]));

        factor();
        if(p[j].equals('*')) j++;
        if(p[j].equals('+'))
        {
            j++;
            term();
        }
    }

    private static void factor()
    {
        System.err.println("factor : " +p[j]+ " isvocab : " +   isvocab(p[j]));
        print();
        if(isvocab(p[j])) j++;
        else
        if(p[j].equals('(')){
            j++;    expression();
            if(p[j].equals(')')) j++;
            else System.out.println("error");
        }
        else  System.out.println("error");
    }

    private static void parse()
    {
        System.out.println("par");
        expression();
        if(!p[j].equals('\0'))  System.out.println("error");
    }

    private static boolean isvocab(String c)
    {

        char x = c.charAt(0);

        return Character.isAlphabetic(x);

    }
    private static void print()
    {
        System.out.println();
        for(int x = 0; x < p.length; x++)
        {
            System.out.print(p[x]);
        }
    }

}



