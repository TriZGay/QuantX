package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.AccountItem;

import java.util.List;

public class AccountsWsMessage implements Message {
    private List<AccountItem> accounts;

    public List<AccountItem> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountItem> accounts) {
        this.accounts = accounts;
    }

    @Override
    public MessageType getType() {
        return MessageType.ACCOUNTS;
    }
}
