package co.istad.idata.feature.review;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.feature.review.dto.ReviewCreateRequest;
import co.istad.idata.feature.review.dto.ReviewResponse;
import co.istad.idata.feature.review.dto.ReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private  final ReviewService reviewService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Page<ReviewResponse> getAllReviewList(

            @RequestParam(required = false,defaultValue = "0")int page,
            @RequestParam(required = false,defaultValue = "12")int size

    ){
        return reviewService.getAllReview(page,size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ReviewResponse createNewReview(@RequestBody ReviewCreateRequest reviewCreateRequest){

        return reviewService.createNewReview(reviewCreateRequest);

    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    ReviewResponse updateReviewById(
            @PathVariable Long id,
            @RequestBody ReviewUpdateRequest reviewUpdateRequest){

        return reviewService.updateReviewById(id,reviewUpdateRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    BasedMessage deleteReviewById(@PathVariable Long id){

        return reviewService.deleteReviewById(id);
    }



}
