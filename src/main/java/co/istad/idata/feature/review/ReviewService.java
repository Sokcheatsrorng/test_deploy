package co.istad.idata.feature.review;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.feature.review.dto.ReviewCreateRequest;
import co.istad.idata.feature.review.dto.ReviewResponse;
import co.istad.idata.feature.review.dto.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {

    // get all list review
    Page<ReviewResponse> getAllReview(int page,int size);

    // create new review
    ReviewResponse createNewReview(ReviewCreateRequest reviewCreateRequest);

    // update review
    ReviewResponse updateReviewById(Long id, ReviewUpdateRequest reviewUpdateRequest);

    // delete review by id
    BasedMessage deleteReviewById(Long id);








}
