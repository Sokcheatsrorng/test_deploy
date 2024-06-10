package co.istad.idata.base;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedError<T> {

    private String code;

    private T description;

}
