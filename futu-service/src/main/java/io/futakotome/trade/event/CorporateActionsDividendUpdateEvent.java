package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CorporateActionsDividendContent;

import java.util.List;

public class CorporateActionsDividendUpdateEvent {
    private List<CorporateActionsDividendContent> contents;

    public CorporateActionsDividendUpdateEvent(List<CorporateActionsDividendContent> contents) {
        this.contents = contents;
    }

    public List<CorporateActionsDividendContent> getContents() {
        return contents;
    }

    public void setContents(List<CorporateActionsDividendContent> contents) {
        this.contents = contents;
    }
}
