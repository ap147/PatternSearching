//Amarjot Parmar (1255668)
//http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou

public class REcompile
{
    static String [] p;
    static int j = 0;
    public static void main(String[] args)
    {
        System.err.println("Expersion : " + args[0]);
        p = args[0].split("");
        parse();
        // System.out.println(isvocab(p[0]));
    }
    private static void parse()
    {
        System.out.println("par");
        expression();
        if(!p[j].equals('\0'))  System.out.println("error");
    }

    private static void expression()
    {
        System.err.println("exp " +p[j]+ " isvocab : " +   isvocab(p[j]));
        term();
        if(isvocab(p[j])||p[j].equals('(')) {

            expression();
        }
    }

    private static void term()
    {
        System.err.println("term "+ p[j]+ " isvocab : " +   isvocab(p[j]));

        factor();
        if(p[j].equals("*")) j++;
        if(p[j].equals("+"))
        {
            j++;
            term();
        }
    }

    private static void factor()
    {
        System.err.println("factor : " +p[j]+ " isvocab : " +   isvocab(p[j]));

        if(isvocab(p[j])) j++;
        else
        if(p[j].equals("(")){
            j++;    expression();
            if(p[j].equals(")")) j++;
            else System.out.println("error");
        }
        else  System.out.println("error");

        print();
    }



    private static boolean isvocab(String c)
    {
        char x = c.charAt(0);
        return Character.isAlphabetic(x);
    }


    private static void print()
    {
        System.out.println();
        for(int x = j; x < p.length; x++)
        {
            System.out.print(p[x]);
        }
        System.out.println();
    }
}




