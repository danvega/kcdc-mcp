package dev.danvega.kcdc;

public record Session(
        String day,
        String time,
        String duration,
        String title,
        String description,
        String type,
        String[] speakers,
        String room,
        String track
) {
}
