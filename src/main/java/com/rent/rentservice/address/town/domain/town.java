package com.rent.rentservice.address.town.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class town {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long townID;
    private String zoneCode;    // 우편번호
    private String address;     // 기본 주소
    private String sido;        // 도/시 이름
    private String sigungu;     // 시/군/구 이름
    private String bname;       // 동 이름

    protected town() {}

    @Builder
    public town(Long townID, String zoneCode, String address, String sido, String sigungu, String bname) {
        this.townID = townID;
        this.zoneCode = zoneCode;
        this.address = address;
        this.sido = sido;
        this.sigungu = sigungu;
        this.bname = bname;
    }
}
