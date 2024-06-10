package co.istad.idata.mapper;

import co.istad.idata.domains.Review;
import co.istad.idata.feature.review.dto.ReviewCreateRequest;
import co.istad.idata.feature.review.dto.ReviewResponse;
import co.istad.idata.feature.review.dto.ReviewUpdateRequest;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


//    @Mapping(target = "userId", source = "user")
    Review fromReviewCreateRequest(ReviewCreateRequest reviewCreateRequest);

    @Mapping(target = "user", source = "user")
    ReviewResponse toReviewResponse(Review review);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromReviewUpdateRequest(ReviewUpdateRequest reviewUpdateRequest,@MappingTarget Review review);


}
