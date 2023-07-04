package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;	
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	UserRepository userRepository;
		
	
	@Transactional(readOnly=true)
	public List<ReviewDTO> findReviewsByMovieId(Long id){
		List<Review> list = reviewRepository.findReviewsByMovieId(id);		
		return list.stream().map(x-> new ReviewDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto)
	{		
		Movie movie = movieRepository.getOne(dto.getMovieId());
		User user = userRepository.getOne(dto.getUser().getId());
		
		Review entity = new Review();
		entity.setText(dto.getText());
		entity.setUser(user);
		entity.setMovie(movie);
		
		entity = reviewRepository.save(entity);		
		return new ReviewDTO(entity);
	}
}
