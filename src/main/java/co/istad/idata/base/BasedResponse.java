package co.istad.idata.base;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedResponse<T> {

    private T payload;

}
