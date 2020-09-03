

/*
 * A class that stores a number of movies in an array
 * If there are n movies, they are stored in the range [0, n-1]
 * Everything else will be null
 */

public class MovieCollection {
	private static final int DEFAULT_SIZE = 10;
	
	Movie[] movies;
	int spotsFilled;
	
	
	public MovieCollection() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * create an empty collection of movies of a certain size
	 * @param size the size that the collection can hold
	 * @throws IllegalArgumentException if size is negative
	 */
	public MovieCollection(int size) {
		if(size <0) throw new IllegalArgumentException("size cannot be negative");
		movies = new Movie[size];
		spotsFilled = 0;
	}
	
	
	/**
	 * This method does the same thing as addMovie from HW9, but also calls sortMovies()
	 * at the end to maintain the sorted array at all times.
	 * @param movie. Must not be null. otherwise throw an IllegalArgumentException
	 * @return true if a movie was added, false if it was full already
	 * @throws IllegalArgumentException for a null movie
	 */
	public boolean addMovie(Movie movie) { 
		if(movie==null)
			throw new IllegalArgumentException("movies cannot be null");
		if(movies.length == spotsFilled)
			return false;
		movies[spotsFilled] = movie;
		spotsFilled++;
		sortMovies();
		return true;
	}
	
	
	
	/**
	 * uses binary search to find the movie.
	 * @param movie to be found
	 * @return true if it the movie is in the collection. Returns false if not, or if movie is null
	 */
	public boolean findMovie(Movie movie) {
		if(movie == null)
			return false;
		
		return (binaryMovieSearch(movie,0,spotsFilled) >= 0);
	}
	
	
	
	
	/**
	 * This is the shell of a recursive version of binary search. It takes in a movie to look for, and the start and end index of where to look for it.
	 * When you are designing a recursive algorithm, think about what a typical iteration will look like, and what the base case will look like.
	 * @param search the movie to search for
	 * @param low the first (lowest) index of the Movies array to look for it (inclusively).
	 * @param high the last (highest) index of the Movies array to look for it (exclusively).
	 * @return The index of the movie if it was found, or -1 if it was not inside the array.
	 */
	private int binaryMovieSearch(Movie search, int low, int high) { 
		if(low == movies.length) // to prevent error
		{
			return -1;
		
		}
		if(movies.length == 0 || movies[low] == null) 
		{
			return -1;
		}

		while(low <= high) 
		{
			int mid = (high +low) / 2; // find midpoint
			if(movies[mid].compareTo(search) == 0) // if its the value we are looking for
			{
				return mid;
				
			}
			if(movies[mid].compareTo(search) == 1) // if mid is bigger
			{
				return binaryMovieSearch(search, low, mid-1); // call lower half
			}
			if(movies[mid].compareTo(search) == -1) // if mid is smaller
			{
				return binaryMovieSearch(search, mid+1, high); //call upper half
			}
		}

	

		//TODO
		//1. base case: if the subarray is empty, return -1
		//2. compute a midpoint
		//3. if the element at the midpoint is what we are looking for
		//		return that midpoint
		//4. if the element at the midpoint is larger
		//		recursively call on the lower half
		//		with the appropriate parameters
		//5. if the element at the midpoint is smaller
		//		recursively call on the upper half
		//		with the appropriate parameters
		//example: if low is 5 and high is 8,
		//		then we are searching over indices 5, 6, and 7
		return -1;
	}

		
	
	
	/**
	 * now that the movies array must be sorted at all times, we can use binary search to find the element faster.
	 * The caveat is that we also must make sure it stays sorted after an element is removed.
	 * You can look back on how the removeMovieSlow method in HW9 did this. 
	 * @param movie. Must not be null. Otherwise throw an IllegalArgumentException
	 * @return false if the movie is not in the array, true if it was found and subsequently removed.
	 */
	public boolean removeMovie(Movie movie) {
		if(movie==null)
			throw new IllegalArgumentException("movies cannot be null");
		
			int found = binaryMovieSearch(movie, 0, spotsFilled);
			if(found == -1)
				return false;
			
			for(int i = found+1; i<spotsFilled;i++) {
				movies[i-1] = movies[i];
			}
			movies[spotsFilled-1] = null;
			spotsFilled--;
		return true;
	}
	
	/**
	 * This is just a simple public way to access the sorting method.
	 * It's not really necessary anymore because the movies should always be sorted.
	 * But it is needed by addMovie.
	 */
	public void sortMovies() {
		quickMovieSort(movies, 0, spotsFilled);
	}
	
	/**
	 * A static method that takes an array of movies and sorts it
	 * Uses the same helper methods as the instance method for sorting
	 * @param movies the array of movies to be sorted
	 */
	public static void sortMovies(Movie[] movies) {
		quickMovieSort(movies, 0, movies.length);
	}
	
	
	/**
	 * The recursive method for the quickSort operation.
	 * @param toSort, the array to be sorted
	 * @param start the starting index for the movies to be sorted
	 * @param num the number of movies, starting from the start index, to include in this sort.
	 */
	private static void quickMovieSort(Movie[] toSort, int start, int num ) {
		if(num <= 1) //program will recurse and split until the num is one
		{
			
		}else { // only runs if we need to sort 2 or more things
			int endIndex = ((num-1)+start); // find end index
			int pivotIndex = (partition(toSort, start, num));
			int f = endIndex-pivotIndex; // find the new num for when we split
			quickMovieSort(toSort, start, pivotIndex - start); // left half
			quickMovieSort(toSort, pivotIndex + 1, f); // right half
			
		
	
			
			
		}
		//TODO
		//1. base case: if the subarray is empty or very small
		//		it doesn't need to be sorted (just return)
		//2. partition this subarray around a pivot (call partition)
		//3. recursively call on the partition left of the pivot
		//4. recursively call on the partition right of the pivot
	}

	
	/**
	 * Private helper method used by quickMovieSort
	 * It only works on a subsection of the array, given by start and num
	 * It should choose a pivot, and move elements
	 * so that everything smaller is to the left
	 * and everything larger is to the right of the pivot
	 * @param toSort, the array to be partitioned
	 * @param start the index of the first element to look at 
	 * @param num how many elements we are looking at
	 * @return the index of the pivot
	 */
	private static int partition(Movie[] toSort, int start, int num)
	{
		int pivotIndex = start + (int)(Math.random() * ((((start+num)-1) - start) + 1)); //picks random pivot index
		Movie pivot = toSort[pivotIndex];
		int lowestIndex = start;
		for(int i=start; i< start+num; i++) 
		{
			if(toSort[i] != null) { // java got mad at me not having this
				if(toSort[i].compareTo(pivot) == -1) // if the movie is smaller than the pivot, it moves it to the left
				{
					if(i == pivotIndex) // if the pivot index is i
					{
						pivotIndex = lowestIndex; // i gets swapped w lowest index, so we swap it here too
					}
					if(lowestIndex == pivotIndex)  //if the pivot is the same as pI
					{
						pivotIndex = i; // the pivot will be swapped to i, so we keep track here
					}
					Movie temp = newMovie(toSort[i]);
					toSort[i] = newMovie(toSort[lowestIndex]);
					toSort[lowestIndex] = newMovie(temp);
					lowestIndex++;

				}
			}
		} 
		Movie temp = newMovie(pivot);
		toSort[pivotIndex] = newMovie(toSort[lowestIndex]); // moves the pivot to the last index to put smaller 
		toSort[lowestIndex] = newMovie(temp); // values to left and bigger values to the right
		pivotIndex = lowestIndex;
		return pivotIndex;
		//TODO
		//1. choose a pivot value
		//		(if you pick poorly, you may not pass testEfficiency)
		//2. loop over the elements in the subarray defined by start and num
		//		keeping track of the lowest index you have not swapped into yet
		//3. when you find an element smaller than the pivot
		//		swap it with the lowest index you have not swapped into yet
		//		if this results in swapping the pivot, keep track of where the pivot is
		//4. after the loop is over, swap the pivot into the lowest index you have not swapped into yet
		//		this puts everything lower (the stuff already swapped) to its left
		//		and everything higher to its right
		//5. return the ending index of the pivot
		
	}
	
	private static Movie newMovie(Movie movie) // returns a new copy of movie given. saves me typing
	{
		return new Movie(new String(movie.getTitle()), movie.getReleaseYear(), movie.getRating());
	}
	
	/**
	 * Return the newest n Movies in a new array.
	 * @param n the number of Movies to return
	 * @return the array of the newest n movies (in the same order they were in)
	 * @throws IllegalArgumentException if n is not valid
	 */
	public Movie[] getNewestMovies(int n) {
		if(n<0 || n>spotsFilled)
			throw new IllegalArgumentException("n has to be greater than 0");
		Movie[] output = new Movie[n];
		for(int i = 0; i<n; i++) {
			output[i] = movies[spotsFilled-n+i];
		}
		return output;
	}

	

	
	
	/**
	 * MovieCollection�s toString should call each movie�s toString method on a different line. 
	 */
	public String toString() {
		String output = "";
		for(int i=0; i<spotsFilled; i++) {
			output = output + movies[i].toString() + "\n";
		}
		
		return output;
		
	}
	
	public int getCapacity() {
		return movies.length;
	}
	
	public int size() {
		return spotsFilled;
	}
	
	public boolean isEmpty() {
		return spotsFilled == 0;
	}
	

	// below is what I used before for pivot finder but it gave me problems. using random was much more efficent
	
//	private static int pivotFinder(Movie[] toSort, int start, int num ) {
	
//	int endIndex = ((num-1)+start);
//	int midIndex = endIndex - start;
//	if(toSort[start].compareTo(toSort[endIndex]) == -1) // if the first value is smaller than last value
//	{
//		if(toSort[start].compareTo(toSort[midIndex]) == -1) // if first value is smaller than mid value
//		{
//			if(toSort[(endIndex)].compareTo(toSort[midIndex]) == -1) // if the last value is smaller than middle value, (and bigger than first value)
//			{
//				return (endIndex); // this is the median value
//			}else { // if mid value is smaller/equal to last value but bigger than the first value
//				return midIndex;
//			}
//		}else { // if the first value is smaller than the last value but bigger than middle value
//			return start;
//		}
//	}else if((toSort[start].compareTo(toSort[midIndex]) == -1)){ // first value bigger than last value but smaller than mid 
//		return start;
//	}else { // first value bigger than last value and bigger than mid
//		if(toSort[endIndex].compareTo(toSort[midIndex]) == -1) // if the last value is smaller than middle value, (and smaller than first value)
//		{
//			return midIndex; // this is the median value
//		}else { // if last value is bigger/equal to mid value but smaller than the first value
//			return endIndex;
//		}
//	}
	

//}

	

}
