/**
 * Created by Joseph on 4/25/17.
 */
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

