package gg.vape.event.impl;

import gg.vape.config.PublicProfileReview;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class PublicProfileReviewEvent
extends Event {
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final PublicProfileReview review;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public PublicProfileReviewEvent(PublicProfileReview publicProfileReview) {
        this.review = publicProfileReview;
    }

    public PublicProfileReview getReview() {
        return this.review;
    }
}
