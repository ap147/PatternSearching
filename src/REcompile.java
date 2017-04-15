//Amarjot Parmar (1255668)


public class REcompile
{


    public static void main(String[] args)
    {

    }


}
class deque
{
    node head;
    node tail;

    public deque()
    {
        head = null;
        tail = null;
    }
    public void insertLeft()
    {
        if(head == null)
        {
            //head == node
            //tail == node
        }
        else
        {
            //node.setnext = head
            //head == node
            //head.prev = null
        }
    }
    public void insertRight()
    {
        if(tail == null)
        {
            //head = node
            //tail = node
        }
        else
        {
            //tail.setnext = node
            //node.setprev = tail
            //tail = node

        }
    }

    public void removeLeft()
    {
        head = head.getNext();
        head.setPrev(null);
    }
    public void removeRight()
    {
        tail = tail.getPrev();
        tail.setNext(null);
    }


    class node
    {
        node next;
        node prev;

        public node()
        {

        }
        protected node getNext()
        {
            return next;
        }
        protected node getPrev()
        {
            return prev;
        }
        protected void setNext(node n)
        {
            next = n;
        }
        protected void setPrev(node p)
        {
            prev = p;
        }
    }

}


