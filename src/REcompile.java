//Amarjot Parmar (1255668)
//http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou

public class REcompile
{   static Character ch [] = new Character[20];
    static Integer next1 [] = new Integer[20];
    static Integer next2 [] = new Integer[20];
    static Integer state=0;
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
        int initial;

        initial=expression();
        if( p[j].equals("1") ) error(); // In C, zero is false, not zero is true
        set_state(state,' ',0,0);
    }

    private static int expression()
    {
        System.err.println("exp " +p[j]+ " isvocab : " +   isvocab(p[j]));
        int r;

        r=term();
        if(isvocab(p[j])||p[j].equals("[")) expression();
        return(r);
    }

    private static int term()
    {
        System.err.println("term "+ p[j]+ " isvocab : " +   isvocab(p[j]));

        int r, t1,t2,f;
        
        f=state-1; r=t1=factor();
        printArrays();
        System.out.println(j);
        if(p[j].equals("*"))
        {
            set_state(state,' ',state+1,t1);
            j++; r=state; state++;
        }
        if(p[j].equals("+")){
            if(next1[f]==next2[f])
                next2[f]=state;
            next1[f]=state;
            f=state-1;
            j++;r=state;state++;
            t2=term();
            set_state(r,' ',t1,t2);
            if(next1[f]==next2[f])
                next2[f]=state;
            next1[f]=state;
        }
        return(r);
    }

    private static int factor()
    {
        System.err.println("factor : " +p[j]+ " isvocab : " +   isvocab(p[j]));

        int r =0;

        if(isvocab(p[j]))
        {
            char x = p[j].charAt(0);
            set_state(state,x,state+1,state+1);
            j++;r=state; state++;
        }
        else
        if(p[j].equals("["))
        {
            j++; r=expression();
            if(p[j].equals("]"))
                j++;
            else
                error();
        }
        else
            error();

        print();
        return(r);
    }



    private static boolean isvocab(String c)
    {
        char x = c.charAt(0);
        return Character.isAlphabetic(x);
    }

    private static void set_state(int s, char c, int n1, int n2)
    {
        ch[s]=c;next1[s]=n1;next2[s]=n2;
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
    private static void printArrays()
    {
        System.out.println();
        System.out.println("s | ch 1 2");
        for(int x =0; x< ch.length; x++)
        {
            System.out.println(x +" | " +ch[x] + " " + next1[x] + " " + next2[x]);
        }
    }
    private static void error()
    {
        System.out.println("Error");
        printArrays();
    }
}




