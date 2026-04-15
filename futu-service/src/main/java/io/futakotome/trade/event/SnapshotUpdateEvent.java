package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.SnapshotContent;

import java.util.List;

public class SnapshotUpdateEvent {
    private List<SnapshotContent> contents;

    public SnapshotUpdateEvent(List<SnapshotContent> snapshotContents) {
        this.contents= snapshotContents;
    }

    public List<SnapshotContent> getContents() {
        return contents;
    }

    public void setContents(List<SnapshotContent> contents) {
        this.contents = contents;
    }
}
