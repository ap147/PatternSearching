/**
 * Created by Amarjot8 on 05-Apr-17.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

class REsearch
{
  private static final char anyChar = '\u0011';
  private static final char branch = '\u0012';

  static LinkedList<FSMState> all_states = new LinkedList<>();
  static MyDeque<FSMState> deque = new MyDeque<>();

  public static void main ( String[] args )
  {
    buildFSM();
    printStates();
    try
    {
      //open file given as argument
      BufferedReader in = new BufferedReader( new FileReader( args[0] ));
      String line;
      String s;
      //print every line which matches pattern
      while ( ( line = in.readLine() ) != null )
      {
        s = line;
        //try match pattern with line, starting at any point
        while ( s.length() > 0)
        {
          if ( search(s) )
          {
            System.out.println( line );
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

    }
  }

  /**
   * does line match FSM pattern
   * @param line input string
   * @return true if string starting at line[0] matches pattern
   */
  static boolean search( String line )
  {
    FSMState current;
    while ( line.length() > 0 )
    {
      current = deque.removeFirst();
      //if scan char...
      if ( current.getId() == -1 )
      {
        //...and deque is empty, FAIL.
        if ( deque.isEmpty() )
        {
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
        deque.addLast( current );
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

  static void buildFSM()
  {
    deque.addLast( new FSMState( -1, '\u0000', new int[] { -1, -1 } ) );
    Scanner s = new Scanner( System.in );
    //read in state machine
    while ( s.hasNextLine() )
    {
      try
      {
        //input symbol for state
        int id = s.nextInt();
        String symbol = s.next();
        if ( symbol.length() != 1 ) throw new Exception();
        int[] nxt = new int[] { s.nextInt(), s.nextInt() };
        all_states.add( id, new FSMState( id, symbol.charAt(0), nxt ) );
      }
      catch ( Exception e )
      {
        System.err.println( "Invalid Input: " + s.next() );
      }
    }
    FSMState initial = all_states.getFirst();
    deque.addFirst( initial );
  }

  static void printStates()
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

class MyDeque<E>
{
  private DequeNode first;
  private DequeNode last;

  MyDeque()
  {
  }

  boolean isEmpty()
  {
    return ( first == null && last == null );
  }

  int length()
  {
    if ( isEmpty() )
    {
      return 0;
    }
    return first.countNodes( 0 );
  }

  void addFirst(E data)
  {
    DequeNode d = new DequeNode( data );
    if( isEmpty() )
    {
      d.next = first;
      first = d;
    }
    else
    {
      first = d;
      last = d;
    }
  }

  E removeFirst()
  {
    DequeNode d = first;
    first = first.next;
    return d.getData();
  }

  E getFirst()
  {
    return first.getData();
  }

  void addLast(E data)
  {
    DequeNode d = new DequeNode(data);
    if( isEmpty() )
    {
      d.prev = last;
      last = d;
    }
    else
    {
      first = d;
      last = d;
    }
  }

  E removeLast()
  {
    DequeNode d = last;
    last = last.prev;
    return d.getData();
  }

  E getLast()
  {
    return last.getData();
  }

  private class DequeNode
  {
    DequeNode prev;
    DequeNode next;
    private E data;

    DequeNode(E data)
    {
      this.data = data;
    }

    E getData()
    {
      return data;
    }

    int countNodes( int n )
    {
      n++;
      if ( next != null )
      {
        return next.countNodes( n );
      }
      return n;
    }
  }
}
