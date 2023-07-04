package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	@Query("SELECT obj "
			+ "FROM Movie obj "
			+ "WHERE (:genreId = obj.genre.id) OR :genreId =0 "
			+ "ORDER BY obj.title")
	Page<Movie> findMoviesByGenre(Long genreId, Pageable pageable);
	
	
}
