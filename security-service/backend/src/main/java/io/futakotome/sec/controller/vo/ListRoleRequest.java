package io.futakotome.sec.controller.vo;

public class ListRoleRequest extends PaginationRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
