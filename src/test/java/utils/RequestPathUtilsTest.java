package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathUtilsTest {
	@ParameterizedTest
	@DisplayName("requestPath를 기반으로 리소스 path를 구할 수 있다.")
	@MethodSource("provideResourcePath")
	public void getResourcePath(String requestPath, String expected) {
		assertThat(RequestPathUtils.getResourcePath(requestPath)).isEqualTo(expected);
	}

	private static Stream<Arguments> provideResourcePath() {
		return Stream.of(
				Arguments.of("/index.html", "./templates/index.html"),
				Arguments.of("/css/bootstrap.min.css", "./static/css/bootstrap.min.css"),
				Arguments.of("/fonts/glyphicons-halflings-regular.eot", "./static/fonts/glyphicons-halflings-regular.eot"),
				Arguments.of("/images/80-text.png", "./static/images/80-text.png"),
				Arguments.of("/js/scripts.js", "./static/js/scripts.js"),
				Arguments.of("/favicon.ico", "./templates/favicon.ico")
		);
	}
}
