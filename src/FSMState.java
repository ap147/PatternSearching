/**
 * Created by Joseph on 4/25/17.
 */
class FSMState
{
    private int id;
    private char pattern;
    private int[] next;

    FSMState( int id, char pattern, int[] nxt )
    {
        this.id = id;
        this.pattern = pattern;
        next = nxt;
    }

    int getId()
    {
        return id;
    }

    char getPattern()
    {
        return pattern;
    }

    int[] getNext()
    {
        return next;
    }
}
