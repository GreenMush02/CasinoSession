package com.casino.casinosession;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;


@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private int id;
    private int playerId;
    private String state;
    private String startTime;
    private String endTime;
}
