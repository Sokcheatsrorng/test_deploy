package co.istad.idata.feature.review;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.domains.Review;
import co.istad.idata.domains.User;
import co.istad.idata.feature.review.dto.ReviewCreateRequest;
import co.istad.idata.feature.review.dto.ReviewResponse;
import co.istad.idata.feature.review.dto.ReviewUpdateRequest;
import co.istad.idata.feature.user.UserRepository;
import co.istad.idata.mapper.ReviewMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private  final ReviewRepository reviewRepository;
    private  final ReviewMapper reviewMapper;
    private  final UserRepository userRepository;


    @Override
    public Page<ReviewResponse> getAllReview(int page, int size) {
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero");
        }
        if (size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size must be greater than or equal to one");
        }
        PageRequest pageRequest = PageRequest.of(page,size);

        Page<Review> reviews = reviewRepository.findAll(pageRequest);

        return reviews.map(reviewMapper::toReviewResponse);
    }

    @Override
    public ReviewResponse createNewReview(ReviewCreateRequest reviewCreateRequest) {

        // check user id
        if (!userRepository.existsById(reviewCreateRequest.userId())){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has been not found!"
            );
        }
        User user = userRepository.findById(reviewCreateRequest.userId())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has been not found!"
                ));

        Review review = reviewMapper.fromReviewCreateRequest(reviewCreateRequest);
        review.setUser(user);
        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public ReviewResponse updateReviewById(Long id, ReviewUpdateRequest reviewUpdateRequest) {
        // check review
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review has been not found!"
                ));
        reviewMapper.fromReviewUpdateRequest(reviewUpdateRequest,review);

        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public BasedMessage deleteReviewById(Long id) {

        // check review
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Review has been not found!"
                ));

        reviewRepository.delete(review);

        return new BasedMessage("Review has been Deleted");
    }


}
