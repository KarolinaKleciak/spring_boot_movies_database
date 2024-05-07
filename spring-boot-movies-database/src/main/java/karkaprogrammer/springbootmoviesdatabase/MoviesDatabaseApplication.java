package karkaprogrammer.springbootmoviesdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.List;

@ComponentScan(basePackages = {"karkaprogrammer.springbootmoviesdatabase"})
@SpringBootApplication
public class MoviesDatabaseApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(MoviesDatabaseApplication.class, args);
	}

	@Autowired
	MovieRepository movieRepository;

	@Override
	public void run(String ... args) {
		MovieDatabaseController dbController = new MovieDatabaseController(movieRepository);

		dbController.fillDatabase();
		dbController.showAllMovies();

		HashMap<String, Object> propertiesToUpdate = new HashMap<>();
		propertiesToUpdate.put("description", "In a world inhabited by anthropomorphic animals in ancient China, a bumbling panda named Po is chosen as the Dragon Warrior destined to protect the Valley of Peace alongside skilled kung fu masters. Together, they must defend their homeland from the evil snow leopard Tai Lung.");
		propertiesToUpdate.put("awards", "Kung Fu Panda was nominated for an Academy Award for Best Animated Feature. It also won several other awards and accolades, including the Annie Award for Best Animated Feature.");
		propertiesToUpdate.put("title", "Kung Fu Panda");
		propertiesToUpdate.put("production_year", 2008);
		propertiesToUpdate.put("category", "Animation, Action, Comedy");
		propertiesToUpdate.put("rating", 8.7);

		dbController.updateMoviesById(2, propertiesToUpdate);
		dbController.deleteMoviesWithTitle("Toy Story 3");

		// Wyszukiwanie filmów po frazie
		String phrase = "2008";
		List<Movie> foundMoviesByPhrase = dbController.searchMoviesByPhrase(phrase);
		if (foundMoviesByPhrase.isEmpty()) {
			System.out.println("\nThere are no movies as result of searching by phrase: " + phrase);
		}else {
			System.out.println("\nThere are " + foundMoviesByPhrase.size() + " movies with phrase: " + phrase);
			for (Movie movie : foundMoviesByPhrase) {
				System.out.println(movie.getTitle());
			}
		}

		// Ocena filmu
		dbController.rateMoviesWithTitle("Harry Potter",9.6);

		// Filtrowanie po kategorii filmów
		String category = "Sci-Fi";
		List<Movie> filteringMoviesByCategory = dbController.filteringByMovieCategory(category);
		if(filteringMoviesByCategory.isEmpty()){
			System.out.println("\nThere are no movies as result of filtering by category: " + category);
		}else {
			System.out.println("\nThere are " + filteringMoviesByCategory.size() + " movies categorized  as: " + category);
			for (Movie movie : filteringMoviesByCategory) {
				System.out.println(movie.getTitle());
			}
		}

		// Wyświetlanie wszystkich filmów po wykonaniu wszystkich operacji
		dbController.showAllMovies();
	}
}
