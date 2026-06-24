package io.futakotome.trade.dto.message;

import java.util.List;

public class CompanyExecutivesContent {
    private List<DirectorInfo> directorList; // 董事高管列表

    public List<DirectorInfo> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<DirectorInfo> directorList) {
        this.directorList = directorList;
    }
}
