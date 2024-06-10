package co.istad.idata.feature.review;

import co.istad.idata.domains.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
