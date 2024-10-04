package com.example.realestate.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.realestate.entity.Booking;
import com.example.realestate.entity.Booking.BookingStatus;
import com.example.realestate.entity.Flat;
import com.example.realestate.entity.User;
import com.example.realestate.exception.CustomException;
import com.example.realestate.repository.BookingRepository;
import com.example.realestate.repository.FlatRepository;


@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private FlatService flatService;

	@Autowired
	private UserService userService;

	@Autowired
	FlatRepository flatRepository;

	 public Booking getBookingById(Long bookingId) {
	        return bookingRepository.findById(bookingId)
	                .orElseThrow(() -> new CustomException("Booking not found for BookingId:" + bookingId));
	    }

	    public List<Booking> getBookingByUserId(Long userId) {
	        List<Booking> bookings = bookingRepository.findByUser_Id(userId);
	        if (bookings.isEmpty()) {
	            throw new CustomException("No bookings available for UserId:" + userId);
	        } else {
	            return bookings;
	        }
	    }

	public List<Booking> getBookingByUserEmail(String email) {

		User userRef = userService.GetByEmail(email);
		Long id = userRef.getId();
		return getBookingByUserId(id);

	}

	public String makeBooking(Booking booking, Long id, Long flatId) {
		Flat flat = flatService.getOneFlat(flatId);
		User user = userService.findById(id);
		if (flat.isAvailable() == true) {
			booking.setUserRef(user);
			booking.setRoomRef(flat);
			booking.setBookingStatus(BookingStatus.CONFIRMED);
			bookingRepository.save(booking);
			flat.setAvailable(false);
			flatRepository.save(flat);
			return "Booking successfull";

		} else {
			throw new CustomException("room is already booked");
		}
	}

	public String cancalBooking(Long bookingId) {
		Booking bookingRef = getBookingById(bookingId);
		
		if (bookingRef == null) {
			throw new CustomException("No Booking available for booking Id:" + bookingId);
		} else {
			Flat flatRef = bookingRef.getFlatRef();
			bookingRef.setBookingStatus(BookingStatus.CANCELLED);
			flatRef.setAvailable(true);
			flatRepository.save(flatRef);
			return "Booking cancel suucessfully";

		}
	}

}
