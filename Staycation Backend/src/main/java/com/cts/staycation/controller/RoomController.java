package com.cts.staycation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.staycation.exception.ResourceNotFoundException;
import com.cts.staycation.model.BookedRoom;
import com.cts.staycation.model.Room;
import com.cts.staycation.response.BookingResponse;
import com.cts.staycation.response.RoomResponse;
import com.cts.staycation.service.BookingService;
import com.cts.staycation.service.IRoomService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
	@Autowired
    private IRoomService roomService;
	
	@Autowired
    private BookingService bookingService;
	
	@CrossOrigin(origins = "http://localhost:5173")
	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(
	        @RequestParam("roomType") String roomType,
	        @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
	    Room savedRoom = roomService.addNewRoom(roomType, roomPrice);
	    RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),
	            savedRoom.getRoomPrice());
	    return ResponseEntity.ok(response);
	}


	
	@CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/room/types")
    public List<String> getRoomTypes() {
        return roomService.getAllRoomTypes();
    }
	
	@CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        
        for (Room room : rooms) {
            RoomResponse response = new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice());
            roomResponses.add(response);
        }
        
        return ResponseEntity.ok(roomResponses);
    }
	
	@CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/delete/room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	
    
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomResponse roomResponse = getRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<Room> availableRooms = roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();
        
        for (Room room : availableRooms) {
            RoomResponse response = new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice());
            roomResponses.add(response);
        }
        
        if(roomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(roomResponses);
        }
    }





    private RoomResponse getRoomResponse(Room room) {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
       List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();
        return new RoomResponse(room.getId(),
                room.getRoomType(), room.getRoomPrice(),
                room.isBooked(), bookingInfo);
    }

    private List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);

    }

}
