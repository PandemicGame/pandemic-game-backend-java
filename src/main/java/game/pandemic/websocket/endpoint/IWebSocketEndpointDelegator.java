package game.pandemic.websocket.endpoint;

import game.pandemic.websocket.auth.IWebSocketAuthenticationObject;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

public non-sealed interface IWebSocketEndpointDelegator<A extends IWebSocketAuthenticationObject, T extends IWebSocketEndpoint<A>> extends IWebSocketEndpoint<A> {
    enum PathMatchingType {
        SPLIT_MATCHING,
        STARTS_WITH_MATCHING,
    }

    default void delegateToEndpoints(final String path, final WebSocketSession session, final A authenticationObject, final String message) {
        final List<T> allEndPoints = getAllEndpoints();
        final Map<T, String> matchingEndPointsWithRestPaths = findEndpointsWithMatchingPath(allEndPoints, path);
        if (matchingEndPointsWithRestPaths.isEmpty()) {
            noValidEndpointsForPathHandler(path);
        } else {
            delegateToEndpoints(matchingEndPointsWithRestPaths, session, authenticationObject, message);
        }
    }

    List<T> getAllEndpoints();

    default Map<T, String> findEndpointsWithMatchingPath(final List<T> endpoints, final String path) {
        return endpoints.stream()
                .map(endpoint -> new AbstractMap.SimpleEntry<>(
                        endpoint,
                        calculateDifferenceBetweenEndpointMappingAndPath(endpoint.getEndpointMapping(), path)
                ))
                .filter(endpointWithPath -> endpointWithPath.getValue().isPresent())
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get()));
    }

    default Optional<String> calculateDifferenceBetweenEndpointMappingAndPath(final String endpointMapping, final String path) {
        return switch (getPathMatchingType()) {
            case SPLIT_MATCHING -> calculateDifferenceBetweenEndpointMappingAndPathBySplitMatching(endpointMapping, path);
            case STARTS_WITH_MATCHING -> calculateDifferenceBetweenEndpointMappingAndPathByStartsWithMatching(endpointMapping, path);
        };
    }

    default PathMatchingType getPathMatchingType() {
        return PathMatchingType.SPLIT_MATCHING;
    }

    default Optional<String> calculateDifferenceBetweenEndpointMappingAndPathBySplitMatching(final String endpointMapping, final String path) {
        final String splitRegex = "(?=/)";
        final String[] endpointMappingParts = endpointMapping.split(splitRegex);
        final String[] pathParts = path.split(splitRegex);

        boolean hasMatched = false;
        int i = 0;
        while (i < endpointMappingParts.length && i < pathParts.length) {
            if (endpointMappingParts[i].equals(pathParts[i])) {
                hasMatched = true;
                i++;
            } else {
                break;
            }
        }

        if (hasMatched) {
            return Optional.of(String.join("", Arrays.asList(pathParts).subList(i, pathParts.length)));
        } else {
            return Optional.empty();
        }
    }

    default Optional<String> calculateDifferenceBetweenEndpointMappingAndPathByStartsWithMatching(final String endpointMapping, final String path) {
        if (path.startsWith(endpointMapping)) {
            return Optional.of(path.replaceFirst(endpointMapping, ""));
        }
        return Optional.empty();
    }

    void noValidEndpointsForPathHandler(final String path);

    default void delegateToEndpoints(final Map<T, String> endpointsWithPaths, final WebSocketSession session, final A authenticationObject, final String message) {
        for (final Map.Entry<T, String> endpointWithPath : endpointsWithPaths.entrySet()) {
            delegateToEndpoint(endpointWithPath.getKey(), endpointWithPath.getValue(), session, authenticationObject, message);
            if (!doesAllowMultipleDelegationsPerPath()) {
                break;
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    default void delegateToEndpoint(final T endpoint, final String path, final WebSocketSession session, final A authenticationObject, final String message) {
        if (endpoint instanceof IWebSocketEndpointConsumer consumer) {
            consumer.consume(session, authenticationObject, message);
        } else if (endpoint instanceof IWebSocketEndpointDelegator delegator) {
            delegator.delegateToEndpoints(path, session, authenticationObject, message);
        }
    }

    default boolean doesAllowMultipleDelegationsPerPath() {
        return false;
    }
}
