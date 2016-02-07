package com.catalina.tokobat.dto;

import com.catalina.tokobat.common.Constants;
import com.catalina.tokobat.entity.Apotek;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alifa on 2/7/2016.
 */
public class ListApotekDto extends ResponseDto {
    List<ApotekDto> apoteks;

    public ListApotekDto(List<Apotek> l) {
        apoteks = new ArrayList<ApotekDto>();
        for(int i=0;i<l.size();i++) {
            Apotek apotek = l.get(i);
            apoteks.add(new ApotekDto(apotek.getId(),apotek.getName(),apotek.getUsername(),apotek.getAddress(),apotek.getLat(),apotek.getLng()));
        }
        this.id= Constants.SUCCESS_INDEX;
        this.message=Constants.DEFAULT_SUCCESS;
    }
}
