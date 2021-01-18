# Manage Your Movies

### Movies2u 
Movies2u allows user to view, add, delete movie.
Code files are available on __master branch__

Below are the rest api exposed for this system:

* Retrieve all available movies
  * Method name = getAllMovies() 
  * Sample URL = http://localhost:8080/api/movie

    
* Retrieve a movie via a movieId
  * Method name = getMovieById
  * Sample URL = http://localhost:8080/api/movie/1


* Retrieve a list of movies via a list of movie ids
  * Method name = getMoviesByIds
  * Sample URL = http://localhost:8080/api/movie/movies?movieIds=1,2  
 
   
* Create a new movie via json which contains movie properties
  * Method name = createNewMovie
  * Sample URL =  http://localhost:8080/api/movie
  * Sample JSON = {
    "title":"Ombak Rindu",
    "category": "Romance",
    "starRating": 2.0
    }  
  
  
* Delete a movie via a movie id
  * Method name = deleteMovie
  * Sample URL = http://localhost:8080/api/movie/delete/3
    

* Update a movie via json which contains any of the movie properties
  * Method name = updateMovie
  * Sample URL = localhost:8080/api/movie/edit/3
  * Sample JSON =   {
    "title":"Project Power",
    "starRating":4
    }
