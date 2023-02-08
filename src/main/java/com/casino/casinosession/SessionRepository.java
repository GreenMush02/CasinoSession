package com.casino.casinosession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import javax.xml.crypto.Data;
import java.util.List;
import java.util.Objects;

@Repository
public class SessionRepository {

    private static final Logger logger = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Session> getAll() {
       return jdbcTemplate.query("SELECT id, playerId, state, startTime, endTime FROM session", BeanPropertyRowMapper.newInstance(Session.class));
    }

    public Session getById(int id){
        return jdbcTemplate.queryForObject("SELECT id, playerId, state, startTime, endTime FROM session WHERE " + "id = ?", BeanPropertyRowMapper.newInstance(Session.class), id);
    }

    public List<Session> getByPlayerId(int playerId){
        return jdbcTemplate.query("SELECT id, playerId, state, startTime, endTime FROM session WHERE " + "playerId = ?", BeanPropertyRowMapper.newInstance(Session.class), playerId);
    }

    public List<Session> getBySessionStateActive(){
        return jdbcTemplate.query("SELECT id, playerId, state, startTime, endTime FROM session WHERE " + "state = 'active'", BeanPropertyRowMapper.newInstance(Session.class));
    }

    public List<Session> getBySessionStateEnd(){
        return jdbcTemplate.query("SELECT id, playerId, state, startTime, endTime FROM session WHERE " + "state = 'end'", BeanPropertyRowMapper.newInstance(Session.class));
    }

    public List<Session> getBySessionDate(String date){
        return jdbcTemplate.query("SELECT id, playerId, state, startTime, endTime FROM session WHERE " + "startTime LIKE ?" , BeanPropertyRowMapper.newInstance(Session.class), date+"%");
    }
    public String startSession (Session session){
        List<Session> playerSessions = getByPlayerId(session.getPlayerId());
        int activeSessions = 0;
        for (Session ses: playerSessions
             ) {
            if (Objects.equals(ses.getState(), "active")) {
                activeSessions += 1;
            }
        }

        if(activeSessions > 0){
            logger.warn("You have got active session!");
            return "You have got active session!";
        } else {
            jdbcTemplate.update("INSERT INTO session(playerId, state, startTime) VALUES(?, ?, ?)",
                    session.getPlayerId(),
                    session.getState(),
                    session.getStartTime()
            );

            return "New session is started";
        }
    }

    public int endSession (Session session){
        jdbcTemplate.update("UPDATE session SET state=?, endTime=? WHERE id=?",
                session.getState(),
                session.getEndTime(),
                session.getId());

        return 1;
    }
}
