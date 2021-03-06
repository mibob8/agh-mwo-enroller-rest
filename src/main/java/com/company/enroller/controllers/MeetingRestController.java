package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController
{
    @Autowired
    MeetingService meetingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings()
    {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public ResponseEntity<?> getMeeting(@PathVariable("id") long id)
    {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/addParticipant/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addMeetingParticipant(@PathVariable("id") long id, @RequestBody Participant participant)
    {
        Meeting meeting = meetingService.findById(id);

        if (meeting == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meeting.addParticipant(participant);

        return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getParticipants/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetingParticipants(@PathVariable("id") long id)
    {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Collection<Participant>>(meeting.getParticipants(), HttpStatus.OK);
    }

    @RequestMapping(value = "/removeMeeting/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> removeMeeting(@PathVariable("id") long id)
    {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null)
        {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        meetingService.remove(meeting);

        return new ResponseEntity<Collection<Meeting>>(meetingService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting)
    {
        Meeting newMeeting = meetingService.findById(meeting.getId());
        if (newMeeting != null)
        {
            return new ResponseEntity("Już dodane", HttpStatus.BAD_REQUEST);
        }

        meetingService.add(newMeeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/updateMeeting", method = RequestMethod.POST)
    public ResponseEntity<?> updateMeeting(@RequestBody Meeting meeting)
    {
        Meeting newMeeting = meetingService.findById(meeting.getId());
        if (newMeeting != null)
        {
            return new ResponseEntity("Nie znaleziono spotkania do aktualizacji", HttpStatus.BAD_REQUEST);
        }

        meetingService.update(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
    }
}
