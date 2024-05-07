package karkaprogrammer.springbootmoviesdatabase;


import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseController {

    private final MovieRepository movieRepository;
    private final List<Movie> movies;

    public MovieDatabaseController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
        this.movies = new ArrayList<Movie>();
        this.movies.add(new Movie("Toy Story 3", 2010, "Sci-Fi", "Toy Story 3 is the third installment in the animated Toy Story  film series produced by Pixar Animation Studios. In this film, the beloved characters from the previous installments, including Woody, Buzz Lightyear, and the rest of the toys, find themselves in a new situation as their owner Andy prepares to leave for college. The toys end up at the Sunnyside Daycare, where they must face new challenges and confront the risk of losing love and friendship. The movie explores themes of friendship, loyalty, and embracing change", 8.4,  "Toy Story 3 won three Oscars, including Best Animated Feature, Best Original Screenplay, and Best Original Song for We Belong Together. It also received numerous other awards and nominations."));
        this.movies.add(new Movie("Avengers", 2012, "Sci-Fi", "Avengers is the first installment in the Marvel Cinematic Universe's Avengers film series. When the world is threatened by the greatest evil, a group of the most powerful superheroes - Iron Man, Thor, Hulk, Captain America, Black Widow, and Hawkeye - join forces to confront their common enemy, Loki, and his Chitauri army.", 8.0, "Avengers won a Teen Choice Award for Best Sci-Fi/Fantasy Movie."));
        this.movies.add(new Movie("Jumanji", 1995, "Adventure, Fantasy", "Jumanji is a film based on Chris Van Allsburg's book. The story revolves around a board game called 'Jumanji', which transports players into a wild, dangerous jungle world where they must survive numerous adventures and challenges to return to reality. The main roles are played by Robin Williams as Alan Parrish, and Kirsten Dunst and Bradley Pierce as the children who discover the mysterious game.", 7.0, "Jumanji won a Saturn Award for Best Fantasy Film. Robin Williams was honored with a Kids' Choice Award for Favorite Movie Actor."));
        this.movies.add(new Movie("Harry Potter", 2001, "Adventure, Fantasy", "Harry Potter and the Sorcerer's Stone is the first film in the Harry Potter series based on the novels by J.K. Rowling. The story follows a young boy named Harry Potter who discovers he is a wizard and is invited to attend Hogwarts School of Witchcraft and Wizardry. There, he learns about his magical heritage and embarks on an adventure to uncover the truth about his past and confront the dark wizard who killed his parents.", 10.0, "Harry Potter and the Sorcerer's Stone received several nominations and awards, including three Academy Award nominations. It was also honored with the BAFTA Children's Award for Best Film."));
        this.movies.add(new Movie("Avengers: Infinity War", 2018, "Sci-Fi", "Avengers: Infinity War is the third installment in the Avengers film series and the culmination of the Marvel Cinematic Universe's first three phases. The Avengers, along with their allies, must be willing to sacrifice everything in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.", 8.4, "Avengers: Infinity War received critical acclaim for its direction, visual effects, and acting, as well as the emotional weight of its story. It grossed over $2 billion worldwide, making it one of the highest-grossing films of all time."));
        this.movies.add(new Movie("Shrek", 2001, "Animation", "Shrek is a 2001 American computer-animated comedy film loosely based on the 1990 fairy tale picture book of the same name by William Steig. The film features the voices of Mike Myers, Eddie Murphy, Cameron Diaz, and John Lithgow. It tells the story of Shrek, a green ogre who sets out on a journey to rescue Princess Fiona from the tower guarded by a dragon, so that Lord Farquaad can marry her.", 10, "Shrek received acclaim from critics, who praised its animation, voice acting, and humor. It was a major box-office success, grossing over $484 million worldwide. The film won the first-ever Academy Award for Best Animated Feature and was nominated for Best Adapted Screenplay."));
    }

    public void fillDatabase(){
        for(Movie movie : this.movies)
        {
            if(movieRepository.findByTitleContainingIgnoreCase(movie.getTitle()).isEmpty())
            {
                movieRepository.save(movie);
                System.out.println("New movie: " + movie.getTitle() + " has been added to the database.");
            }
            else
            {
                System.out.println("Movie: " + movie.getTitle() + " already exists...");
            }
        }
    }

    public void showAllMovies(){
        List<Movie> allMovies = this.movieRepository.findAll();
        if (!allMovies.isEmpty()) {
            System.out.println("ALL movies in the database:");
            allMovies.forEach(System.out::println);
        } else {
            System.out.println("There are no movies in the database.");
        }
    }

    public void updateMoviesById(int id, HashMap<String, Object> propertiesToUpdate){
        Optional<Movie> movieAfterUpdate = movieRepository.findById(1);
        if (movieAfterUpdate.isPresent()) {
            Movie movieToUpdate = movieAfterUpdate.get();

            if(propertiesToUpdate.containsKey("category")) movieToUpdate.setCategory((String) propertiesToUpdate.get("category"));
            if(propertiesToUpdate.containsKey("description")) movieToUpdate.setDescription((String) propertiesToUpdate.get("description"));
            if(propertiesToUpdate.containsKey("rating")) movieToUpdate.setRating((Double) propertiesToUpdate.get("rating"));
            if(propertiesToUpdate.containsKey("awards")) movieToUpdate.setAwards((String)propertiesToUpdate.get("awards"));
            if(propertiesToUpdate.containsKey("title")) movieToUpdate.setTitle((String) propertiesToUpdate.get("title"));
            if(propertiesToUpdate.containsKey("production_year")) movieToUpdate.setProductionYear((int)propertiesToUpdate.get("production_year"));
            movieRepository.save(movieToUpdate);

            System.out.println("\nMovie after update:");
            System.out.println(movieToUpdate);
        } else {
            System.out.println("\nMovie with the provided ID was not found.");
        }
    }

    public void deleteMoviesWithTitle(String title){
        List<Movie> moviesToDelete = this.movieRepository.findByTitleContainingIgnoreCase(title);

        if(!moviesToDelete.isEmpty()) {
            for (Movie movie : moviesToDelete) {
                this.movieRepository.delete(movie);
            }
            System.out.println("\nMovies deleted successfully.");
        } else {
            System.out.println("\nThere is no movie to delete with this title: " + title);
        }
    }


    public List<Movie> searchMoviesByPhrase(String phrase){
        return this.movieRepository.findAll().stream()
                .filter(movie -> containsPhrase(movie, phrase))
                .collect(Collectors.toList());
    }

    private boolean containsPhrase(Movie movie, String phrase) {
        String phraseLower = phrase.toLowerCase();
        return movie.getTitle().toLowerCase().contains(phraseLower)
                || movie.getDescription().toLowerCase().contains(phraseLower)
                || movie.getCategory().toLowerCase().contains(phraseLower)
                || String.valueOf(movie.getRating()).equals(phraseLower)
                || movie.getAwards().toLowerCase().contains(phraseLower)
                || String.valueOf(movie.getProductionYear()).equals(phraseLower);
    }

    public void rateMoviesWithTitle(String title, double newRating) {
        List<Movie> moviesToRate = this.movieRepository.findByTitleContainingIgnoreCase(title);
        for (Movie movieToRate : moviesToRate) {
            double oldRating = movieToRate.getRating();
            System.out.println("\nMovie rating:\nMovie named " + movieToRate.getTitle() + ": ");
            System.out.println("Old rating: " + oldRating);
            movieToRate.setRating(newRating);
            this.movieRepository.save(movieToRate);
            System.out.println("New rating: " + movieToRate.getRating());
        }
    }


    public List<Movie> filteringByMovieCategory (String category){
       return this.movieRepository.findByCategoryIgnoreCase(category);
    }
}
