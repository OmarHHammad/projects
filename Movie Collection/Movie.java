
public class Movie {
	private final String title;
	private double rating;
	private final int releaseYear;
	
	
	
	/**
	 * assigns the 3 values to their respective fields.
	 * @throws an illegalArgmuent if any of the 3 input values are out of range or null.
	 * @param name 
	 * @param releaseYear
	 * @param rating
	 */
	Movie(String name, int releaseYear, double rating){
		if(name == null) throw new IllegalArgumentException("name cannot be null");
		if(releaseYear > 2020 || releaseYear < 1888) throw new IllegalArgumentException("year has to be within 1888 to 2020");
		if(rating < 0 || rating > 10) throw new IllegalArgumentException("rating must be between 0 and 10");
		this.title = name;
		this.rating = rating;
		this.releaseYear = releaseYear;
	}
	
	
	/**
	 * Compares movies by release Year, and then alphabetically by title for all movies in that specific year
	 * @param movie
	 * @return 1 if this movie is 'greater' than the movie given,
	 * 0 if they are the same, 
	 * and -1 if this movie is 'less' than the given movie.
	 */
		
	public int compareTo(Movie movie) {
		if(this.releaseYear < movie.releaseYear)
			return -1;
		else if (this.releaseYear > movie.releaseYear)
			return 1;
		
		if(this.title.compareTo(movie.title)<0) {
			return -1;
		} else if(this.title.compareTo(movie.title)>0) {
			return 1;
		}
		if(this.rating < movie.rating)
			return -1;
		else if (this.rating > movie.rating)
			return 1;
		
		//must have the same releaseYear and title to return 0
		return 0;
		
	}
	
	
	/**
	 * setter for Rating. Follows the same rules as in the constructor
	 * @param newRating
	 */
	public void setRating(double newRating) {
		if(newRating<0||newRating>10)throw new IllegalArgumentException("rating must be between 0 and 10");
		this.rating = newRating;
	}
	
	
	/**
	 * Format: <title>: Released in <releaseYear>, Rating <rating>
	 */
	public String toString() {
		return this.title + ": Released in " + releaseYear + ", Rating " + this.rating;
	}


	@Override 
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Movie))
			return false;
		Movie other = (Movie) obj;
		if (Double.doubleToLongBits(rating) != Double.doubleToLongBits(other.rating))
			return false;
		if (releaseYear != other.releaseYear)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
	//getters go here.
	public String getTitle() {
		return title;
	}


	public double getRating() {
		return rating;
	}


	public int getReleaseYear() {
		return releaseYear;
	}


	
	
	
}
