package io.futakotome.sec.controller.vo;

public class ListUserRequest extends PaginationRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
