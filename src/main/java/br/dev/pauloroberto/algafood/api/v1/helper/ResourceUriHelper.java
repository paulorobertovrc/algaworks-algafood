package br.dev.pauloroberto.algafood.api.v1.helper;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

@UtilityClass
public class ResourceUriHelper {
    public static void addUriInResponseHeader(Object resourceId) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        HttpServletResponse response = Objects.requireNonNull(requestAttributes).getResponse();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();

        Objects.requireNonNull(response).setHeader(HttpHeaders.LOCATION, uri.toString());
    }

}
