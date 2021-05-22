package io.github.offjaao.algalog.api.exceptionhandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class SimpleField {

    private final String name;
    private final String message;

}
