package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {
	
	@Autowired
	ParticipantService participantService;
	
	@Autowired
	MeetingService meetingService;



	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long identifier) {
	     Meeting meeting = meetingService.findByID(identifier);
	     if (meeting == null) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
	
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deleteMeeting(@PathVariable("id") long identifier){
		 Meeting meeting = meetingService.findByID(identifier);
		 if(meeting == null) {
			 return new ResponseEntity("Unable to delete. A meeting with login " + identifier + " doesn't exist.", HttpStatus.CONFLICT);
		 }
		 else {
			 meetingService.delete(meeting);
		 }
		 return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerParticipant(@RequestBody Meeting meeting){
		 Meeting foundMeeting =  meetingService.findByID(meeting.getId());
		 if(foundMeeting != null) {
			 return new ResponseEntity("Unable to create. A meeting with ID " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
		 }
		 else {
			 meetingService.add(meeting);
		 }
		 return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
//	
	
//	@RequestMapping(value = "/{id}/participants/{participantid}", method = RequestMethod.POST)
//	public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") int id, @PathVariable("participantid") ...TODO) {
//			//pobrac cpotkanie
//			//pobrac uczestnika
//		
//		//dodać uczestnika do spotkania
//		}
//	     return new ResponseEntity<Participant>(participant, HttpStatus.OK);
//	 }
//	

}
