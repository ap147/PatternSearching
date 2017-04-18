/**
 * Created by Amarjot8 on 05-Apr-17.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

class REsearch
{
  static LinkedList<FSMState> all_states = new LinkedList<>();
  static Deque<FSMState> deque = new LinkedList<>();

  public static void main ( String[] args )
  {
    buildFSM();
    try
    {
      BufferedReader in = new BufferedReader( new FileReader( args[0] ));
      String line, s;
      while ( ( line = in.readLine() ) != null )
      {
        s = line;
        while ( s.length() > 0)
        {
          if ( search(s) ) System.out.println( line );
          else s = s.substring(1);
        }
      }

    }
    catch ( Exception e )
    {

    }
  }

  static boolean search( String line )
  {
    FSMState current;
    while ( line.length() > 0 )
    {
      current = deque.removeFirst();
      if ( current.getId() == -1 )
      {
        if ( deque.isEmpty() )
        {
          return false;
        }
        else deque.addLast( current );
      }
      //branching symbol. tbd
      else if ( current.getPattern() ==  '\u0000' )
      {
        for ( int n : current.getNext() )
        {
          deque.addFirst( all_states.get(n) );
        }
      }
      else if ( current.getPattern() == line.charAt( 0 ) )
      {
        current = all_states.get( current.getNext()[0] );
        if ( current.getNext()[0] == 0 ) return true;
        deque.addLast( current );
        line = line.substring(1);
      }
      else return false;
    }
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
  DequeNode<E> first;
  DequeNode<E> last;

  MyDeque()
  {
  }

  void addFirst(E data)
  {
    DequeNode d = new DequeNode(data);
    if( first != null )
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

  E getFirst()
  {
    DequeNode d = first;
    first = first.next;
    return d.getData();
  }

  void addLast(E data)
  {
    DequeNode d = new DequeNode(data);
    if( last != null )
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

  E getLast()
  {
    DequeNode d = last;
    last = last.prev;
    return d.getData();
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

  }
}

