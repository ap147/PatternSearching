/**
 * Created by Amarjot8 on 05-Apr-17.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

class REsearch
{
  private static final int scanID = -1;
  private static final char anyChar = '\u0011';
  private static final char branch = '\u0012';

  private static LinkedList<FSMState> all_states = new LinkedList<>();
  private static MyDeque<FSMState> deque = new MyDeque<>();

  public static void main ( String[] args )
  {
    buildFSM();
    try
    {
      //open file given as argument
      BufferedReader in = new BufferedReader( new FileReader( args[0] ));
      String line;
      String s;
      boolean match;
      //print every line which matches pattern
      while ( ( line = in.readLine() ) != null )
      {
        match = false;
        s = line;
        //try match pattern with line, starting at any point
        while ( s.length() > 0 && !match )
        {
          if ( search(s) )
          {
            System.out.println( line );
            match = true;
          }
          else
          {
            s = s.substring(1);
          }
        }
      }

    }
    catch ( Exception e )
    {
        e.printStackTrace();
    }
  }

  /**
   * does line match FSM pattern
   * @param line input string
   * @return true if string starting at line[0] matches pattern
   */
  private static boolean search( String line )
  {
    deque.addLast( new FSMState( scanID, '\u0000', new int[] { -1, -1 } ) );
    FSMState initial = all_states.getFirst();
    deque.addFirst( initial );
    FSMState current;
    while ( line.length() > 0 )
    {
      current = deque.removeFirst();
      //if scan char...
      if ( current.getId() == scanID )
      {
        //...and deque is empty, FAIL.
        if ( deque.isEmpty() )
        {
          deque.addLast( current );
          return false;
        }
        //...and deque is not empty, move scan char to end of deque; current states = next states
        else
        {
          deque.addLast( current );
        }
      }
      //if branching state, then add both states to current states
      else if ( current.getPattern() ==  branch )
      {
        for ( int n : current.getNext() )
        {
          deque.addFirst( all_states.get(n) );
        }
      }
      //if match, consume and add next to next states
      else if ( current.getPattern() == line.charAt( 0 ) || current.getPattern() == anyChar )
      {
        current = all_states.get( current.getNext()[0] );
        //if final state reached, SUCCESS!
        if ( current.getNext()[0] == 0 )
        {
          return true;
        }
        deque.addLast( all_states.get( current.getNext()[0] ) );
        line = line.substring(1);
      }
      //if not match and not special case, FAIL.
      else
      {
        return false;
      }
    }
    //if end of string reached and not at success state, FAIL.
    return false;
  }

  private static void buildFSM()
  {

    Scanner s = new Scanner( System.in );
    //read in state machine
    while ( s.hasNextInt() )
    {
      try
      {
        //input symbol for state
        int id = s.nextInt();
        String symbol = s.next();
        if ( symbol.length() != 1 )
        {
            System.err.println("symbol too long");
            throw new Exception();
        }
        int[] nxt = new int[] { s.nextInt(), s.nextInt() };
        all_states.add( id, new FSMState( id, symbol.charAt(0), nxt ) );
      }
      catch ( Exception e )
      {
        System.err.println( "Invalid Input: " );
        printStates();
      }
    }

  }

  private static void printStates()
  {
    int[] next;
    for ( FSMState s : all_states )
    {
      System.out.print(s.getId() + " " + s.getPattern());
      next = s.getNext();
      for ( int i : next)
      {
        System.out.print(" " + Integer.toString( i ) );
      }
      System.out.println();
    }
  }

}