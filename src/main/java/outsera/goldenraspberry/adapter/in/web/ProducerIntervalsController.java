package outsera.goldenraspberry.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import outsera.goldenraspberry.adapter.in.web.dto.ProducerIntervalItemResponse;
import outsera.goldenraspberry.adapter.in.web.dto.ProducerIntervalsResponse;
import outsera.goldenraspberry.application.port.in.GetProducerIntervalsUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@Tag(name = "Producers", description = "Operations related to producers and awards intervals")
public class ProducerIntervalsController {

    private final GetProducerIntervalsUseCase useCase;

    public ProducerIntervalsController(GetProducerIntervalsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/producers/intervals", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get award intervals for producers",
            description = "Returns the producers with the minimum and maximum intervals between consecutive wins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Intervals calculated",
                            content = @Content(schema = @Schema(implementation = ProducerIntervalsResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
            }
    )
    public ProducerIntervalsResponse intervals() {
        var result = useCase.getIntervals();

        Function<outsera.goldenraspberry.domain.model.ProducerInterval, ProducerIntervalItemResponse> mapper =
                i -> new ProducerIntervalItemResponse(i.producer(), i.interval(), i.previousWin(), i.followingWin());

        return new ProducerIntervalsResponse(
                result.min().stream().map(mapper).toList(),
                result.max().stream().map(mapper).toList()
        );
    }
}
