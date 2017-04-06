/**
 * Created by Amarjot8 on 05-Apr-17.
 */
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

class REsearch
{
  public static void main ( String[] args )
  {
    Deque deque = new LinkedList<FSMState>();
    deque.add( new FSMState( -1, '\u0000' ) );
    Scanner s = new Scanner( System.in );
    while ( s.hasNextLine() )
    {
      try
      {
        //the state number
        int state = s.nextInt();
        //input symbol for state
        char symbol = s.next().charAt( 0 );
        for ( FSMState fsm_state : deque )
        {
          if ( fsm_state.state() == state )
            fsm_state.setPattern( symbol );
        }
        //possible next states
        int nxt_1 = s.nextInt();
        int nxt_2 = s.nextInt();
      }
      catch ( Exception e )
      {
        System.err.println( "Invalid Input: " + s.next() );
      }
    }
  }
}

class FSMState
{
  private int id;
  private char pattern;
  FSMState( int id, char pattern )
  {
    this.id = id;
    this.pattern = pattern;
  }
  FSMState( int id )
  {
    this.id = id;
  }
  void setPattern(char pattern) throws Exception
  {
    if ( this.pattern == '\u0000' ) this.pattern = pattern;
    else throw new Exception();
  }
  int state()
  {
    return id;
  }
  char match()
  {
    return pattern;
  }
}
