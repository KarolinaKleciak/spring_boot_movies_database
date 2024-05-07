package karkaprogrammer.springbootmoviesdatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByTitleContainingIgnoreCase(String keyword);
    List<Movie> findByCategoryIgnoreCase(String category);

}