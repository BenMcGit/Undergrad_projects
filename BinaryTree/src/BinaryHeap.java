// BinaryHeap class
//
// CONSTRUCTION: empty or with initial array.
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************


/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinaryHeap {
    /**
     * Construct the binary heap.
     */
    public BinaryHeap( ) {
        currentSize = 0;
        array = new Comparable[ DEFAULT_CAPACITY + 1 ];
    }
    
    /**
     * Construct the binary heap from an array.
     * @param items the inital items in the binary heap.
     */
    public BinaryHeap( Comparable [ ] items ) {
        currentSize = items.length;
        array = new Comparable[ items.length + 1 ];
        
        for( int i = 0; i < items.length; i++ )
            array[ i + 1 ] = items[ i ];
        buildHeap( );
    }
    
    /**
     * Insert into the priority queue.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( Comparable x ) {
        if( currentSize + 1 == array.length )
            doubleArray( );
        
        // Percolate up
        int hole = ++currentSize;
        array[ 0 ] = x;
        
        for( ; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
            array[ hole ] = array[ hole / 2 ];
        array[ hole ] = x;
    }
    
    
    
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item.
     */
    public Comparable findMin( ) {
        if( isEmpty( ) )
            System.out.println( "Empty binary heap" );
        return array[ 1 ];
    }
    
    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item.
     */
    public Comparable deleteMin( ) {
        Comparable minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );
        
        return minItem;
    }
    
    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( ) {
        for( int i = currentSize / 2; i > 0; i-- )
            percolateDown( i );
    }
    
    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return currentSize == 0;
    }
    
    /**
     * Returns size.
     * @return current size.
     */
    public int size( ) {
        return currentSize;
    }
    
    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( ) {
        currentSize = 0;
    }
    
    private static final int DEFAULT_CAPACITY = 100;
    
    private int currentSize;      // Number of elements in heap
    private Comparable [ ] array; // The heap array
    
    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole ) {
        int child;
        Comparable tmp = array[ hole ];
        
        for( ; hole * 2 <= currentSize; hole = child ) {
            child = hole * 2;
            if( child != currentSize &&
                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                child++;
            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                break;
        }
        array[ hole ] = tmp;
    }
    
    /**
     * Internal method to extend array.
     */
    private void doubleArray( ) {
        Comparable [ ] newArray;
        
        newArray = new Comparable[ array.length * 2 ];
        for( int i = 0; i < array.length; i++ )
            newArray[ i ] = array[ i ];
        array = newArray;
    }
    }