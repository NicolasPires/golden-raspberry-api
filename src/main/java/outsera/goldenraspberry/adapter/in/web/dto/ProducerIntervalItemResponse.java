package outsera.goldenraspberry.adapter.in.web.dto;

public record ProducerIntervalItemResponse(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {}
