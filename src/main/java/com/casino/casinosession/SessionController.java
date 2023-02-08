package com.casino.casinosession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class SessionController {

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime ldt = LocalDateTime.now();
    String localDateTime = ldt.format(CUSTOM_FORMATTER);

    @Autowired
    SessionRepository sessionRepository;

    @GetMapping("/test")
    public int test () {
        return 1;
    }


    @GetMapping("/sessions")
    public List<Session> getAll() {
        return sessionRepository.getAll();
    }

    @GetMapping("/sessions/{id}")
    public Session getById(@PathVariable("id") int id){
        return sessionRepository.getById(id);
    }

    @GetMapping("/sessions/player/{playerId}")
    public List<Session> getByPlayerId(@PathVariable("playerId") int playerId){
        return sessionRepository.getByPlayerId(playerId);
    }

    @GetMapping("/sessions/state/active")
    public List<Session> getBySessionStateActive() {
        return sessionRepository.getBySessionStateActive();
    }

    @GetMapping("/sessions/state/end")
    public List<Session> getBySessionStateEnd() {
        return sessionRepository.getBySessionStateEnd();
    }

    @GetMapping("/sessions/date/{dt}")
    public List<Session> getBySessionDate(@PathVariable("dt") String date){
        return sessionRepository.getBySessionDate(date);
    }



    @PostMapping("/session/start")
    public String startSession(@RequestBody Session session){
        session.setState("active");
        session.setStartTime(localDateTime);
        return sessionRepository.startSession(session);
    }

    @PatchMapping("/session/end/{id}")
    public int endSession(@PathVariable("id") int id){

        Session session = sessionRepository.getById(id);

        if(session != null){
            session.setState("end");
            session.setEndTime(localDateTime);

            return sessionRepository.endSession(session);

        } else {
            return -1;
        }



    }
}
