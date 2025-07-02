package com.neoCamp.footballMatch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectConfrontationDTO {
    private Long clubAId;
    private Long clubBId;
    private Long games;
    private Long victoriesClubA;
    private Long victoriesClubB;
    private Long draw;
    //empates = draw
}